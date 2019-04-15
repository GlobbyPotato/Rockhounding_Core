package com.globbypotato.rockhounding_core.items;

public class BaseContainer extends BaseItem {
	public BaseContainer(String modid, String name){
		super(modid, name);
		setMaxStackSize(1);
		setContainerItem(this);
	}
}