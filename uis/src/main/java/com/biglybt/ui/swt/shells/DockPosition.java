/*
 * Copyright (C) Azureus Software, Inc, All Rights Reserved.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details ( see the LICENSE file ).
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.biglybt.ui.swt.shells;

/**
 * A simple class to declare a docking position and an offset; currently only used by <code>ShellDocker</code>
 *
 * @author khai
 */
public class DockPosition {
	public static final int TOP_LEFT = 1;

	public static final int BOTTOM_LEFT = 2;

	public static final int TOP_RIGHT = 3;

	public static final int BOTTOM_RIGHT = 4;

	private int position = TOP_LEFT;

	private Offset offset = new Offset(0, 0);

	public DockPosition() {
		this(TOP_LEFT, null);
	}

	public DockPosition(int position, Offset offset) {
		if (position == TOP_LEFT || position == TOP_RIGHT
			|| position == BOTTOM_LEFT || position == BOTTOM_RIGHT) {
			this.position = position;
		} else {
			this.position = TOP_LEFT;
		}
		if (null != offset) {
			this.offset = offset;
		}
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Offset getOffset() {
		return offset;
	}

	public void setOffset(Offset offset) {
		if (null != offset) {
			this.offset = offset;
		}
	}
}
