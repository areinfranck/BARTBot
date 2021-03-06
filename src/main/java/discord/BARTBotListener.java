package discord;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class BARTBotListener extends ListenerAdapter {

    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {

        //Parses Discord message into String array
        String[] messageSentArray = event.getMessage().getContentRaw().split(" ");

        //Returns if sender is bot (to prevent the bot from listening to itself)
        if (Objects.requireNonNull(event.getMember()).getUser().isBot()) {
            return;
        }

        //Checks if first element is the !bart command
        if (!messageSentArray[0].equalsIgnoreCase("!bart")) {
            return;
        }

        //Displays error message if no arguments are provided
        if (messageSentArray.length == 1) {
            new BARTBotErrorMessage(event, "Invalid Arguments.",
                    "Please enter a valid command. Do !help for a list of useful commands.").sendErrorMessage();
            return;
        }

        //Switch statement that checks arguments and calls respective classes
        switch (messageSentArray[1].toLowerCase()) {
            case "map":
                new BARTBotSystemMap().getStationMap(event);
                break;
            case "advisories":
                new BARTBotAdvisories().getAdvisories(event);
                break;
            case "elevators":
                new BARTBotElevators().getElevators(event);
                break;
            case "trains":
                new BARTBotTrainCount().getTrainCount(event);
                break;
            case "abbreviations":
                new BARTBotAbbreviations().getAbbreviations(event);
                break;
            case "departures":
                if (messageSentArray.length < 3) {
                    new BARTBotErrorMessage(event, "Insufficient Abbreviations.",
                            "Please enter origin and destination stations. Type '!BART Abbreviations' " +
                                    "for a list of station abbreviations.").sendErrorMessage();
                    return;
                }
                new BARTBotDepartures(messageSentArray[2].toLowerCase()).getDepartures(event);
                break;
            case "fare":
                if (messageSentArray.length < 4) {
                    new BARTBotErrorMessage(event, "Insufficient Abbreviations.",
                            "Please enter origin and destination stations. Type '!BART Abbreviations' " +
                                    "for a list of station abbreviations.").sendErrorMessage();
                    return;
                }
                new BARTBotFareCalculator(messageSentArray[2].toLowerCase(), messageSentArray[3].toLowerCase()).getFare(event);
                break;
            default:
                new BARTBotErrorMessage(event, "Invalid Arguments.",
                        "Please enter a valid command. Do !help for a list of useful commands.").sendErrorMessage();
                break;
        }
    }
}