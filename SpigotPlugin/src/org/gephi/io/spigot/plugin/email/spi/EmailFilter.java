/*
Copyright 2008-2010 Gephi
Authors : Yi Du <duyi001@gmail.com>
Website : http://www.gephi.org

This file is part of Gephi.

Gephi is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Gephi is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Gephi.  If not, see <http://www.gnu.org/licenses/>.
*/
package org.gephi.io.spigot.plugin.email.spi;

import javax.mail.Message;
import org.gephi.io.importer.api.Report;

/**
 *
 * @author Yi Du
 */
public interface EmailFilter {
    /**
     * 
     * @param message message to filter
     * @param filter constraints used to filter message
     * @param report report to report panel
     * @return false if message isn't filtered by the filter
     *          true if message is filtered by the filter;符合过滤条件
     */
    public boolean filterEmail(Message message, String filter, Report report);

    /**
     *
     * @return own filter type
     */
    public String getFilterType();
}
