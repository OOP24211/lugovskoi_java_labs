package org.gogil.chat.model;

public class AuthMessage extends Message {
    private String password;
    private boolean success;
    private String errorText;

    public AuthMessage() {}

    public AuthMessage(MessageType type, String userName, String password) {
        super(type, null, userName);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorText() {
        return errorText;
    }
    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }
}
