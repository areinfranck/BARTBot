package discord;

import computation.AddDatedFooter;
import wrapper.APIAdvisories;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class BARTBotAdvisories {

    //Builds the Discord embed for the advisories API
    public void getAdvisories(GuildMessageReceivedEvent event) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.GREEN);
        eb.setTitle(":information_source: Advisories");
        eb.addField("Current system advisories:", new APIAdvisories().getAdvisories(), true);
        eb.setFooter(new AddDatedFooter().addDatedFooter());
        event.getChannel().sendMessage(eb.build()).queue();
    }
}