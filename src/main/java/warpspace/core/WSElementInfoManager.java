package warpspace.core;

import api.utils.element.Blocks;
import org.schema.game.common.data.element.ElementInformation;
import org.schema.game.common.data.element.ElementKeyMap;

import static org.schema.game.common.data.element.ElementKeyMap.descriptionTranslations;
import static org.schema.game.common.data.element.ElementKeyMap.nameTranslations;

public class WSElementInfoManager {
	public static void onBlockConfigLoad() {
		nameTranslations.remove(Blocks.JUMP_DRIVE_ROOT.getId());
		nameTranslations.remove(Blocks.JUMP_DISTANCE_1.getId());
		nameTranslations.remove(Blocks.JUMP_DISTANCE_2.getId());
		nameTranslations.remove(Blocks.JUMP_DISTANCE_3.getId());

		descriptionTranslations.remove(Blocks.JUMP_DRIVE_ROOT.getId());

		Blocks.JUMP_DRIVE_ROOT.getInfo().description = "If server settings require it, this chamber will enable the Warp Drive on your ship.\r\nOtherwise, it simply acts as a hub for Warp Drive upgrade chambers.";

		Blocks.JUMP_DRIVE_ROOT.getInfo().name = "Warp Drive Base";
		Blocks.JUMP_DISTANCE_1.getInfo().name = "Warp Flight Speed 1";
		Blocks.JUMP_DISTANCE_2.getInfo().name = "Warp Flight Speed 2";
		Blocks.JUMP_DISTANCE_3.getInfo().name = "Warp Flight Speed 3";
		short[] chIDs = { //other jump chamber IDs
				1088, 1089, 1090, //power efficiency 1,2,3
				1112, //multicharge
				1117, //autocharge
				127, 126, 12, //charge time 1,2,3
				13, 17, 1112 //multicharge 1,2,3
		};
		ElementInformation block;
		for(short id : chIDs) {
			block = ElementKeyMap.getInfo(id);
			nameTranslations.remove(id);
			block.name = block.name.replace("Jump", "Warp Drive");
		}
	}
}
