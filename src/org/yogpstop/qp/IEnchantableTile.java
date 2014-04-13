/*
 * Copyright (C) 2012,2013 yogpstop
 * This program is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the
 * GNU Lesser General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package org.yogpstop.qp;

import java.util.List;

public interface IEnchantableTile {
	
	void G_reinit();

	byte getEfficiency();
	byte getFortune();
	byte getUnbreaking();
	boolean getSilktouch();

	void set(byte efficiency, byte fortune, byte unbreaking, boolean silktouch);
	void setFortuneConfig( Boolean include, List<Long> list );
	void setSilctouchConfig( Boolean include, List<Long> list );
	
	List<Long> getFortuneList();
	List<Long> getSilktouchList();
	boolean getFortuneInclude();
	boolean getSilktouchInclude();
}
