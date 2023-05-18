package assignments.assignment4.gui.member;
//interface yang diterapkan pada AbstractMemberGUI
//mempunyai method login, logout, dan getPageName
public interface Loginable {
    boolean login(String id, String password);
    void logout();
    String getPageName();

}
