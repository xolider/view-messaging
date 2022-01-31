package ovh.vicart.messaging.sample;

import ovh.vicart.messaging.MessagingAPI;
import ovh.vicart.messaging.models.User;

import java.io.*;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class Messaging {

    private MessagingAPI api;
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private final PrintStream writer = System.out;
    private String version;

    public Messaging() {
        try(InputStream is = getClass().getClassLoader().getResourceAsStream("version.properties")) {
            Properties p = new Properties();
            p.load(is);
            version = p.getProperty("version");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        writer.println("Messaging v" + version);
        writer.println();
        try {
            String username = askForUsername();
            api = MessagingAPI.getInstanceFor(username);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int option = showMenuAndGetOption();
        switch (option) {
            case 1:
                try {
                    List<User> list = api.getOnlineUsers().get();
                    for(User u: list) {

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private String askForUsername() throws IOException {
        writer.print("Entrez votre nom d'utilisateur: ");
        return reader.readLine();
    }

    private int showMenuAndGetOption() {
        int option = -1;
        while (option < 0 || option > 4) {
            writer.println("Choisissez une option:");
            writer.println("1: Afficher les utilisateurs en ligne");
            writer.println("4: Quitter");
            try {
                option = Integer.parseInt(reader.readLine());
            } catch (IOException ignored) {

            }
        }
        return option;
    }
}
