package com.globbypotato.rockhounding_core.items;

public class BaseConsumable extends BaseItem {
	public BaseConsumable(String name, int uses){
		super(name);
		this.setMaxDamage(uses);
		this.setMaxStackSize(1);
	}
}