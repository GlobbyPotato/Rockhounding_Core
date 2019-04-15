package com.globbypotato.rockhounding_core.items;

public class BaseConsumable extends BaseItem {
	public BaseConsumable(String modid, String name, int uses){
		super(modid, name);
		this.setMaxDamage(uses);
		this.setMaxStackSize(1);
	}
}