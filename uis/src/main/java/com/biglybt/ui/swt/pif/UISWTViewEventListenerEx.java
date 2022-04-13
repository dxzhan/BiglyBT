package com.biglybt.ui.swt.pif;

import com.biglybt.pif.PluginInterface;

import java.util.List;

/**
 * @apiNote Plugin3D uses this
 * @deprecated Use {@link UISWTInstance#registerView(String, UISWTViewBuilder)}
 * or {@link UISWTInstance#registerView(Class, UISWTViewBuilder)}
 */
public interface
UISWTViewEventListenerEx
	extends UISWTViewEventListener {
	public UISWTViewEventListenerEx
	getClone();

	public default CloneConstructor
	getCloneConstructor() {
		return (null);
	}

	public interface
	CloneConstructor {
		public PluginInterface
		getPluginInterface();

		public String
		getIPCMethod();

		public default List<Object>
		getIPCParameters() {
			return (null);
		}
	}
}	
