package com.globbypotato.rockhounding_core.items.io;

import com.globbypotato.rockhounding_core.handlers.Reference;
import com.globbypotato.rockhounding_core.items.BaseContainer;

public class ContainerIO extends BaseContainer{

	public ContainerIO(String name) {
		super(Reference.MODID, name);
		setMaxStackSize(1);
		setCreativeTab(Reference.RockhoundingCore);
	}

}