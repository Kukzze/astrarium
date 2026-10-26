package com.kukzze.astrarium;

import lombok.Getter;

@Getter
public class CombatLevelMath
{
	private final int combatLevel;
	private final double preciseCombatLevel;
	private final double progressToNext; // 0.0 to 1.0

	private final int attackNeeded;
	private final int strengthNeeded;
	private final int defenceNeeded;
	private final int hitpointsNeeded;
	private final int prayerNeeded;
	private final int rangedNeeded;
	private final int magicNeeded;

	public CombatLevelMath(int att, int str, int def, int hp, int pray, int range, int mage)
	{
		double base = 0.25 * (def + hp + Math.floor(pray / 2.0));
		double melee = 0.325 * (att + str);
		double ranged = 0.325 * Math.floor(3.0 * range / 2.0);
		double magic = 0.325 * Math.floor(3.0 * mage / 2.0);

		double maxStyle = Math.max(melee, Math.max(ranged, magic));
		this.preciseCombatLevel = base + maxStyle;
		this.combatLevel = Math.min(126, (int) Math.floor(preciseCombatLevel));
		this.progressToNext = combatLevel >= 126 ? 1.0 : Math.min(1.0, Math.max(0.0, preciseCombatLevel - combatLevel));

		int nextCombat = combatLevel + 1;

		if (combatLevel >= 126)
		{
			this.attackNeeded = -1;
			this.strengthNeeded = -1;
			this.defenceNeeded = -1;
			this.hitpointsNeeded = -1;
			this.prayerNeeded = -1;
			this.rangedNeeded = -1;
			this.magicNeeded = -1;
		}
		else
		{
			this.attackNeeded = findLevelsNeeded(nextCombat, att, str, def, hp, pray, range, mage, SkillType.ATTACK);
			this.strengthNeeded = findLevelsNeeded(nextCombat, att, str, def, hp, pray, range, mage, SkillType.STRENGTH);
			this.defenceNeeded = findLevelsNeeded(nextCombat, att, str, def, hp, pray, range, mage, SkillType.DEFENCE);
			this.hitpointsNeeded = findLevelsNeeded(nextCombat, att, str, def, hp, pray, range, mage, SkillType.HITPOINTS);
			this.prayerNeeded = findLevelsNeeded(nextCombat, att, str, def, hp, pray, range, mage, SkillType.PRAYER);
			this.rangedNeeded = findLevelsNeeded(nextCombat, att, str, def, hp, pray, range, mage, SkillType.RANGED);
			this.magicNeeded = findLevelsNeeded(nextCombat, att, str, def, hp, pray, range, mage, SkillType.MAGIC);
		}
	}

	private enum SkillType
	{
		ATTACK, STRENGTH, DEFENCE, HITPOINTS, PRAYER, RANGED, MAGIC
	}

	private static int findLevelsNeeded(int targetCombat, int att, int str, int def, int hp, int pray, int range, int mage, SkillType type)
	{
		if (targetCombat > 126)
		{
			return -1;
		}

		int currentSkill = (type == SkillType.ATTACK ? att
			: type == SkillType.STRENGTH ? str
			: type == SkillType.DEFENCE ? def
			: type == SkillType.HITPOINTS ? hp
			: type == SkillType.PRAYER ? pray
			: type == SkillType.RANGED ? range
			: mage);

		for (int add = 1; currentSkill + add <= 99; add++)
		{
			int testAtt = att + (type == SkillType.ATTACK ? add : 0);
			int testStr = str + (type == SkillType.STRENGTH ? add : 0);
			int testDef = def + (type == SkillType.DEFENCE ? add : 0);
			int testHp = hp + (type == SkillType.HITPOINTS ? add : 0);
			int testPray = pray + (type == SkillType.PRAYER ? add : 0);
			int testRange = range + (type == SkillType.RANGED ? add : 0);
			int testMage = mage + (type == SkillType.MAGIC ? add : 0);

			double base = 0.25 * (testDef + testHp + Math.floor(testPray / 2.0));
			double melee = 0.325 * (testAtt + testStr);
			double ranged = 0.325 * Math.floor(3.0 * testRange / 2.0);
			double magic = 0.325 * Math.floor(3.0 * testMage / 2.0);

			double total = base + Math.max(melee, Math.max(ranged, magic));
			if ((int) Math.floor(total) >= targetCombat)
			{
				return add;
			}
		}
		return -1;
	}
}
