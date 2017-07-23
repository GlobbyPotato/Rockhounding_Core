package com.globbypotato.rockhounding_core.items;

public class BaseUtil extends BaseItem {
	public BaseUtil(String name){
		super(name);
		setMaxStackSize(1);
		setContainerItem(this);
	}
}