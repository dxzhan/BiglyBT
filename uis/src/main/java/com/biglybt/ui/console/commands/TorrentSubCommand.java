/*
 * Created on 21/12/2004
 * Created by Paul Duran
 * Copyright (C) 2004 Aelitis, All Rights Reserved.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 *
 * AELITIS, SARL au capital de 30,000 euros
 * 8 Allee Lenotre, La Grille Royale, 78600 Le Mesnil le Roi, France.
 *
 */

package com.biglybt.ui.console.commands;

import com.biglybt.core.download.DownloadManager;
import com.biglybt.ui.console.ConsoleInput;

import java.util.List;

/**
 * simple subclass of TorrentCommand that publicises the performCommand
 * method. the execute() method is not necessarily used in these objects
 *
 * @author pauld
 */
public abstract class TorrentSubCommand extends TorrentCommand {
	/**
	 * @param commandNames
	 * @param action
	 */
	public TorrentSubCommand(String command_name, String short_name) {
		super(command_name, short_name, null);
	}

	@Override
	public abstract boolean performCommand(ConsoleInput ci, DownloadManager dm, List<String> args);
}
