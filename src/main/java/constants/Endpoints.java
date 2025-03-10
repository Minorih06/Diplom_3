package constants;

public enum Endpoints {
    HOME_PAGE("https://stellarburgers.nomoreparties.site/"),
    REGISTRATION_PAGE("https://stellarburgers.nomoreparties.site/register"),
    LOGIN_PAGE("https://stellarburgers.nomoreparties.site/login"),
    PERSONAL_ACCOUNT_PAGE("https://stellarburgers.nomoreparties.site/account/profile"),
    PASSWORD_RECOVERY_PAGE("https://stellarburgers.nomoreparties.site/forgot-password");

    private final String url;

    Endpoints(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return url;
    }
}
