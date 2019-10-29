//@@author shutingy
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;
import seedu.address.ui.ResultDisplay;

/**
 * Change the theme of the GUI
 */
public class SetThemeCommand extends Command {

    public static final String COMMAND_WORD = "theme";

    public static final String MESSAGE_SUCCESS = "Theme changed to %s";

    private String styleSheet;
    private String color;
    /**
     * Creates a SetThemeCommand to change the theme of the gui
     */
    public SetThemeCommand(String styleSheet, String color) {
        this.styleSheet = styleSheet;
        this.color = color;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        /* todo: gui version
        System.out.println(Main.class.getResourceAsStream("/images/Light.png"));
        Image light = new Image(Main.class.getResourceAsStream("/images/Light.png"));
        Image dark = new Image(Main.class.getResourceAsStream("/images/Dark.png"));
        System.out.println(light.getUrl());
        ImageView iv = new ImageView();
        AnchorPane an = new AnchorPane();
        an.getChildren().add(iv);
        Scene scene = new Scene(an);
        Scene scene = new Scene(new ThemeWindow().getView());
        Stage stage = new Stage();
        stage.setTitle("Theme");
        stage.show();

        GuiSettings guiSettings = model.getGuiSettings();
        GuiSettings newGuiSettings = new GuiSettings(guiSettings.getWindowWidth(),
                guiSettings.getWindowHeight(), guiSettings.getWindowCoordinates().x,
                guiSettings.getWindowCoordinates().y, styleSheet);
        model.setGuiSettings(newGuiSettings);

        */
        requireNonNull(model);
        MainWindow.setStylesheet(styleSheet);
        ResultDisplay.setColor(color);
        model.setStyleSheet(styleSheet);
        return new CommandResult(String.format(MESSAGE_SUCCESS, styleSheet));
    }
}
