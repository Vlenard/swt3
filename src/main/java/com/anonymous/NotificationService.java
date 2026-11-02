package com.anonymous;

/**
 * Értesítési szolgáltatás interfész.
 */
public interface NotificationService {

    /**
     * Értesítés küldése.
     *
     * @param message az elküldendő üzenet szövege
     */
    void send(String message);
}
