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

package com.biglybt.ui.swt.skin;

import com.biglybt.ui.swt.debug.ObfuscateImage;
import com.biglybt.ui.swt.pif.PluginUISWTSkinObject;
import com.biglybt.ui.swt.views.skin.SkinView;
import org.eclipse.swt.widgets.Control;

/**
 * @author TuxPaper
 * @created Jun 12, 2006
 */
public interface SWTSkinObject
	extends PluginUISWTSkinObject {
	/**
	 * Retrieve the associated SWT Control used by the skin object
	 *
	 * @return SWT Control
	 */
	public Control getControl();

	/**
	 * Retrieve the type of widget.
	 *
	 * @return TODO Move widget types to SWTSkinObject
	 */
	public String getType();

	/**
	 * Retrieve the Skin Object ID that represents this object.  Typically the
	 * same as {@link #getConfigID()}, however, may be different if a config
	 * id is used to make independant copies
	 *
	 * @return An unique Skin Object ID
	 */
	public String getSkinObjectID();

	/**
	 * Retrieve the Config ID which is ID in the skin config file.
	 *
	 * @return Config ID
	 */
	public String getConfigID();

	public SWTSkinObject getParent();

	public SWTSkin getSkin();

	public boolean isVisible();

	public void setVisible(boolean visible);

	public void setDefaultVisibility();

	/**
	 * @param sConfigID
	 * @param sSuffix
	 */
	void setBackground(String sConfigID, String sSuffix);

	/**
	 * @param suffix
	 * @param level
	 * @param walkUp TODO
	 * @return TODO
	 */
	String switchSuffix(String suffix, int level, boolean walkUp);

	String switchSuffix(String suffix, int level, boolean walkUp, boolean walkDown);

	/**
	 * Convenience method for switching suffix using defaults
	 *
	 * @param suffix
	 * @return
	 */
	String switchSuffix(String suffix);

	/**
	 * @return
	 */
	String getSuffix();

	/**
	 * @return
	 */
	SWTSkinProperties getProperties();

	/**
	 * @param properties
	 */
	void setProperties(SWTSkinProperties skinProperties);

	//void getTemplate(String property)

	public void addListener(SWTSkinObjectListener listener);

	public void removeListener(SWTSkinObjectListener listener);

	/**
	 * @return
	 */
	public SWTSkinObjectListener[] getListeners();

	public String getViewID();

	/**
	 * @param eventType
	 */
	void triggerListeners(int eventType);

	/**
	 * @param eventType
	 * @param params
	 */
	void triggerListeners(int eventType, Object params);

	/**
	 * @since 3.0.1.3
	 */
	public void dispose();

	/**
	 * @param id
	 * @since 3.0.4.3
	 */
	void setTooltipID(String id);

	/**
	 * @return
	 * @since 3.0.5.3
	 */
	boolean getDefaultVisibility();

	public Object getData(String id);

	public void setData(String id, Object data);

	/**
	 * @return
	 * @since 3.1.1.1
	 */
	boolean isDisposed();

	/**
	 * @return
	 * @since 3.1.1.1
	 */
	boolean isDebug();

	/**
	 * @param b
	 * @since 3.1.1.1
	 */
	public void setDebug(boolean b);

	/**
	 * @param walkup
	 * @return
	 * @since 3.1.1.1
	 */
	String getTooltipID(boolean walkup);

	public void relayout();

	public void layoutComplete();

	public void setObfuscatedImageGenerator(ObfuscateImage obfuscatedImageGenerator);

	public SkinView getSkinView();

	public void setSkinView(SkinView sv);
}
