package br.com.mobilemind.api.droidutil.rest;

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

public class RestException extends RuntimeException {

    private int httpSatatus = -1;

    public RestException(String message, Exception e) {
        super(message, e);
    }

    public RestException(Exception e) {
        super(e);
    }

    public RestException(String message) {
        super(message);
    }

    public RestException() {
        super("unknown exception");
    }

    public RestException(int httpStatus, String message) {
        super(message);
        this.httpSatatus = httpStatus;
    }

    public RestException(int httpStatus) {
        this.httpSatatus = httpStatus;
    }

    public int getHttpSatatus() {
        return httpSatatus;
    }
}
