package com.globbypotato.rockhounding_core.items.io;

import com.globbypotato.rockhounding_core.handlers.Reference;
import com.globbypotato.rockhounding_core.items.BaseItem;

public class UtilIO extends BaseItem {
	public UtilIO(String name){
		super(Reference.MODID, name);
		setMaxStackSize(1);
		setCreativeTab(Reference.RockhoundingCore);
	}
}