package dev.jonaz.vured.bot.control.reaction

import net.dv8tion.jda.api.entities.Member

interface ReactionHandler {
    fun execute(member: Member)
}
