/*
 * Copyright (C) Bigly Software, Inc, All Rights Reserved.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA
 */

package com.biglybt.core.tracker.client.impl;

import java.net.URL;

import com.biglybt.core.tracker.client.TRTrackerAnnouncerRequest;
import com.biglybt.core.util.HashWrapper;

public class 
TRTrackerAnnouncerRequestImpl
	implements TRTrackerAnnouncerRequest
{
	final private long			session_id;
	final private HashWrapper	hash;
	final private boolean		is_stop;
	final private URL			url;
	final private long			sent;
	final private long			received;
	
	private long			elapsed;
	
	public
	TRTrackerAnnouncerRequestImpl()
	{
		session_id	= 0;
		url			= null;
		hash		= null;
		is_stop		= false;
		sent		= 0;
		received	= 0;
		elapsed		= -1;
	}
	
	public
	TRTrackerAnnouncerRequestImpl(
		long		_session_id,
		HashWrapper	_hash,
		boolean		_is_stop,
		URL			_url,
		long		_sent,
		long		_received )
	{
		session_id	= _session_id;
		hash		= _hash;
		is_stop		= _is_stop;
		url			= _url;
		sent		= _sent;
		received	= _received;
	}
	
	@Override
	public long
	getSessionID()
	{
		return( session_id );
	}
	
	@Override
	public URL
	getURL()
	{
		return( url );
	}
	
	@Override
	public HashWrapper
	getHash()
	{
		return( hash );
	}
	
	@Override
	public boolean 
	isStopRequest()
	{
		return( is_stop );
	}
	
	@Override
	public long
	getReportedUpload()
	{
		return( sent );
	}
	
	@Override
	public long
	getReportedDownload()
	{
		return( received );
	}
	
	public void
	setElapsed(
		long	t )
	{
		elapsed = t;
	}
	
	@Override
	public long 
	getElapsed()
	{
		return( elapsed );
	}
}
