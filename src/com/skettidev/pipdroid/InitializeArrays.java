package com.skettidev.pipdroid;

public class InitializeArrays {
	public static void all() {
		main_buttons();
		special_stat_values();
		skill_stat_values();
		submenu_special();
		submenu_skills();
	}

	public static void main_buttons() {
		VarVault.MAIN_BUTTONS.add(VarVault.stats);
		VarVault.MAIN_BUTTONS.add(VarVault.items);
		VarVault.MAIN_BUTTONS.add(VarVault.data);
	}

	public static void special_stat_values() {
		VarVault.SPECIAL_STAT_VALUES.add(VarVault.strength);
		VarVault.SPECIAL_STAT_VALUES.add(VarVault.perception);
		VarVault.SPECIAL_STAT_VALUES.add(VarVault.endurance);
		VarVault.SPECIAL_STAT_VALUES.add(VarVault.charisma);
		VarVault.SPECIAL_STAT_VALUES.add(VarVault.intelligence);
		VarVault.SPECIAL_STAT_VALUES.add(VarVault.agility);
		VarVault.SPECIAL_STAT_VALUES.add(VarVault.luck);
	}

	public static void skill_stat_values() {
		VarVault.SKILL_STAT_VALUES.add(VarVault.barter);
		VarVault.SKILL_STAT_VALUES.add(VarVault.big_guns);
		VarVault.SKILL_STAT_VALUES.add(VarVault.energy);
		VarVault.SKILL_STAT_VALUES.add(VarVault.explosives);
		VarVault.SKILL_STAT_VALUES.add(VarVault.lockpick);
		VarVault.SKILL_STAT_VALUES.add(VarVault.medicine);
		VarVault.SKILL_STAT_VALUES.add(VarVault.melee);
		VarVault.SKILL_STAT_VALUES.add(VarVault.repair);
		VarVault.SKILL_STAT_VALUES.add(VarVault.science);
		VarVault.SKILL_STAT_VALUES.add(VarVault.small_guns);
		VarVault.SKILL_STAT_VALUES.add(VarVault.sneak);
		VarVault.SKILL_STAT_VALUES.add(VarVault.speech);
		VarVault.SKILL_STAT_VALUES.add(VarVault.unarmed);
	}

	public static void submenu_special() {
		VarVault.SUBMENU_SPECIAL.add(VarVault.str);
		VarVault.SUBMENU_SPECIAL.add(VarVault.strSTAT);
		VarVault.SUBMENU_SPECIAL.add(VarVault.per);
		VarVault.SUBMENU_SPECIAL.add(VarVault.perSTAT);
		VarVault.SUBMENU_SPECIAL.add(VarVault.end);
		VarVault.SUBMENU_SPECIAL.add(VarVault.endSTAT);
		VarVault.SUBMENU_SPECIAL.add(VarVault.chr);
		VarVault.SUBMENU_SPECIAL.add(VarVault.chrSTAT);
		VarVault.SUBMENU_SPECIAL.add(VarVault.intel);
		VarVault.SUBMENU_SPECIAL.add(VarVault.intelSTAT);
		VarVault.SUBMENU_SPECIAL.add(VarVault.agi);
		VarVault.SUBMENU_SPECIAL.add(VarVault.agiSTAT);
		VarVault.SUBMENU_SPECIAL.add(VarVault.luk);
		VarVault.SUBMENU_SPECIAL.add(VarVault.lukSTAT);
	}

	public static void submenu_skills() {
		VarVault.SUBMENU_SKILLS.add(VarVault.bart);
		VarVault.SUBMENU_SKILLS.add(VarVault.barterSTAT);
		VarVault.SUBMENU_SKILLS.add(VarVault.bgns);
		VarVault.SUBMENU_SKILLS.add(VarVault.big_gunsSTAT);
		VarVault.SUBMENU_SKILLS.add(VarVault.nrg);
		VarVault.SUBMENU_SKILLS.add(VarVault.energySTAT);
		VarVault.SUBMENU_SKILLS.add(VarVault.expl);
		VarVault.SUBMENU_SKILLS.add(VarVault.explosivesSTAT);
		VarVault.SUBMENU_SKILLS.add(VarVault.lock);
		VarVault.SUBMENU_SKILLS.add(VarVault.lockpickSTAT);
		VarVault.SUBMENU_SKILLS.add(VarVault.medi);
		VarVault.SUBMENU_SKILLS.add(VarVault.medicineSTAT);
		VarVault.SUBMENU_SKILLS.add(VarVault.mlee);
		VarVault.SUBMENU_SKILLS.add(VarVault.meleeSTAT);
		VarVault.SUBMENU_SKILLS.add(VarVault.rpar);
		VarVault.SUBMENU_SKILLS.add(VarVault.repairSTAT);
		VarVault.SUBMENU_SKILLS.add(VarVault.sci);
		VarVault.SUBMENU_SKILLS.add(VarVault.scienceSTAT);
		VarVault.SUBMENU_SKILLS.add(VarVault.sgns);
		VarVault.SUBMENU_SKILLS.add(VarVault.small_gunsSTAT);
		VarVault.SUBMENU_SKILLS.add(VarVault.snek);
		VarVault.SUBMENU_SKILLS.add(VarVault.sneakSTAT);
		VarVault.SUBMENU_SKILLS.add(VarVault.spch);
		VarVault.SUBMENU_SKILLS.add(VarVault.speechSTAT);
		VarVault.SUBMENU_SKILLS.add(VarVault.uarm);
		VarVault.SUBMENU_SKILLS.add(VarVault.unarmedSTAT);
	}

	public static void bottom_bar_stats() {
		VarVault.BOTTOM_BAR_STATS.add(VarVault.status);
		VarVault.BOTTOM_BAR_STATS.add(VarVault.statusLL);
		VarVault.BOTTOM_BAR_STATS.add(VarVault.special);
		VarVault.BOTTOM_BAR_STATS.add(VarVault.specialLL);
		VarVault.BOTTOM_BAR_STATS.add(VarVault.skills);
		VarVault.BOTTOM_BAR_STATS.add(VarVault.skillsLL);
		VarVault.BOTTOM_BAR_STATS.add(VarVault.perks);
		VarVault.BOTTOM_BAR_STATS.add(VarVault.perksLL);
		VarVault.BOTTOM_BAR_STATS.add(VarVault.general);
		VarVault.BOTTOM_BAR_STATS.add(VarVault.generalLL);
	}

	public static void bottom_bar_items() {
		VarVault.BOTTOM_BAR_ITEMS.add(VarVault.weapons);
		VarVault.BOTTOM_BAR_ITEMS.add(VarVault.weaponsLL);
		VarVault.BOTTOM_BAR_ITEMS.add(VarVault.apparel);
		VarVault.BOTTOM_BAR_ITEMS.add(VarVault.apparelLL);
		VarVault.BOTTOM_BAR_ITEMS.add(VarVault.aid);
		VarVault.BOTTOM_BAR_ITEMS.add(VarVault.aidLL);
		VarVault.BOTTOM_BAR_ITEMS.add(VarVault.misc);
		VarVault.BOTTOM_BAR_ITEMS.add(VarVault.miscLL);
		VarVault.BOTTOM_BAR_ITEMS.add(VarVault.ammo);
		VarVault.BOTTOM_BAR_ITEMS.add(VarVault.ammoLL);
	}
}
