package dev.jonaz.vacegaming.musicbot.service

import dev.jonaz.vacegaming.musicbot.reaction.ReactionMessageCase
import dev.jonaz.vacegaming.musicbot.util.data.Translation
import dev.jonaz.vacegaming.musicbot.util.application.ifFalse
import dev.jonaz.vacegaming.musicbot.util.koin.genericInject
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageEmbed
import java.awt.Color
import java.util.*

class StaticMessageService {
    private val musicChannelService by genericInject<MusicChannelService>()
    private val reactionService by genericInject<ReactionService>()
    private val musicService by genericInject<MusicService>()

    private lateinit var message: Message

    fun createBaseMessage() = build(
            title = Translation.NO_TRACK_TITLE,
            description = Translation.NO_TRACK_DESCRIPTION,
            color = Color.RED,
            volume = null
    ).also {
        val channel = musicChannelService.getTextChannel()

        musicChannelService.clearMessages()
        channel?.sendMessage(it)?.queue { message ->
            this.message = message
            setReactions()
        }
    }

    fun build(
            title: String?,
            description: String?,
            color: Color?,
            volume: Int?
    ): MessageEmbed {
        val queue = musicService.getQueue()
        val trackQueue = mutableListOf<String>()

        queue.forEach { trackQueue.add(it.info.title ?: Translation.UNKNOWN_TITLE) }

        return EmbedBuilder().apply {
            title?.let {
                if (title.isBlank()) {
                    this.setTitle(Translation.UNKNOWN_TITLE)
                } else {
                    this.setTitle(title)
                }
            }

            description?.let {
                if (description.isBlank()) {
                    this.setDescription(Translation.UNKNOWN_DESCRIPTION)
                } else {
                    this.setDescription(description)
                }
            }

            color?.let {
                this.setColor(color)
            }

            volume?.let {
                this.addField(Translation.VOLUME, "$volume%", true)
            }

            queue.isEmpty().ifFalse {
                this.addField(
                        "${Translation.QUEUE} (${trackQueue.size})",
                        trackQueue.joinToString("\n").take(1024),
                        false
                )
            }
        }.run { return@run this.build() }
    }

    fun set(message: MessageEmbed) {
        try {
            this.message.editMessage(message).complete()
        } catch (e: Exception) {
            createBaseMessage()
            Thread.sleep(500)
        }

    }

    private fun setReactions() {
        reactionService.getReactions(ReactionMessageCase.STATIC).run {
            this.forEach { message.addReaction(it.emote).queue() }
        }
    }
}
