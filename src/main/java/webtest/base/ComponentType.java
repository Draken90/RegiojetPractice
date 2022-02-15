package webtest.base;

/**
 * This enum represents set of known and standardized components which helps to describe web elements to user.
 * Main use is to provide better navigation for user regarding of point of problem in error messages.
 * This enum can contains only visually clearly described components, for example BUTTON, PANEL, TITLE etc.
 * Only that kind of STANDARDIZED components what END USER can easily imagine and what ALREADY knows can be included!
 */
public enum ComponentType {

    /**
     * Never use this option!
     */
    @Deprecated
    UNDEFINED("UNDEFINED"),

    ANY("any"),

    TITLE("Title of page"),
    DIALOG_CAPTION("Dialog caption"),
    DIALOG_BUTTON("Dialog button"),

    MAIN_MENU_BUTTON("Main Menu Button"),
    MAIN_MENU_ITEM("Main Menu Item"),
    SUB_MENU_ITEM("Sub Menu Item"),
    HAMBURGER_MENU("Hamburger menu"),

    /**
     * Not table row, but page row ... for example one payment on bulk payment
     */
    ROW("Row"),

    BUTTON("Button"),
    NAVIGATION_LINK("NavigationLink"),
    LINK("Link"),

    LABEL("Label"),
    MESSAGE("Message"),

    TAB("Tab"),

    TEXT_INPUT("TextInput"),
    CHECK_BOX("CheckBox"),
    COMBO_BOX("ComboBox"),
    RADIO_ITEM("RadioItem"),
    DATE_PICKER("DatePicker"),

    TABLE("table"),
    TABLE_COLUMN("table/row/column"),
    TABLE_ROW("table/row"),

    SECTION("section"),

    IMAGE("image"),

    FRAME("frame"),

    INPUT("input"),

    /**
     * Something like small modal mini dialog with content opened when clicked on some other component.
     */
    POP_UP_WINDOW("pop-up window");

    private String userFriendlyName;

    ComponentType(String userFriendlyName) {
        this.userFriendlyName = userFriendlyName;
    }

    public String getUserFriendlyName() {
        return userFriendlyName;
    }
}
