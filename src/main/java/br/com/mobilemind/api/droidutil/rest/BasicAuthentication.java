package br.com.mobilemind.api.droidutil.rest;

import br.com.mobilemind.api.security.key.Base64;

/*
 * #%L
 * Mobile Mind - Droid Util
 * %%
 * Copyright (C) 2012 Mobile Mind Empresa de Tecnologia
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
/**
 *
 * @author Ricardo Bocchi
 */
public class BasicAuthentication {

    public static final String AUTHENTICATION = "Authentication";

    private String realm;
    private String username;
    private String password;

    public BasicAuthentication() {
    }

    public BasicAuthentication(String username, String password) {
        this(null, username, password);
    }

    public BasicAuthentication(String realm, String username, String password) {
        this.realm = realm;
        this.username = username;
        this.password = password;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return username + ":" + password;
    }

    public String toBase64() {
        return Base64.encodeBytes(toString().getBytes());
    }
}
