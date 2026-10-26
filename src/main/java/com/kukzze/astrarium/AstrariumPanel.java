package com.kukzze.astrarium;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import net.runelite.api.Client;
import net.runelite.api.QuestState;
import net.runelite.api.Skill;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;

public class AstrariumPanel extends PluginPanel
{
	private static final String TAB_RADAR = "RADAR";
	private static final String TAB_QUESTS = "QUESTS";
	private static final String TAB_DIARIES = "DIARIES";
	private static final String TAB_COMBAT = "COMBAT";
	private static final String TAB_UTILITIES = "UTILITIES";
	private static final String TAB_SESSION = "SESSION";

	private final AstrariumPlugin plugin;
	private final AstrariumConfig config;

	// Header elements
	private final JPanel headerCard;
	private final JLabel nameLabel;
	private final JLabel combatLevelLabel;
	private final JProgressBar combatProgressBar;
	private final JLabel nextCombatMathLabel;
	private final JLabel totalLevelLabel;

	// Tabs
	private final CardLayout cardLayout = new CardLayout();
	private final JPanel contentCards = new JPanel(cardLayout);

	private final JPanel radarContainer = new JPanel();
	private final JPanel questsContainer = new JPanel();
	private final JPanel diariesContainer = new JPanel();
	private final JPanel combatContainer = new JPanel();
	private final JPanel utilitiesContainer = new JPanel();
	private final JPanel sessionContainer = new JPanel();

	private final JButton radarNavBtn = new JButton("\uD83E\uDDED Radar");
	private final JButton questsNavBtn = new JButton("\uD83D\uDDFA\uFE0F Quests");
	private final JButton diariesNavBtn = new JButton("\uD83D\uDCDC Diary");
	private final JButton combatNavBtn = new JButton("\u2694\uFE0F PvM");
	private final JButton utilitiesNavBtn = new JButton("\uD83C\uDF3E Farm");
	private final JButton sessionNavBtn = new JButton("\uD83D\uDCCA Log");

	// Selectors
	private final JComboBox<Skill> skillSelector = new JComboBox<>(new Skill[]{
		Skill.AGILITY, Skill.THIEVING, Skill.PRAYER, Skill.CONSTRUCTION,
		Skill.HUNTER, Skill.MAGIC, Skill.RANGED, Skill.ATTACK, Skill.STRENGTH,
		Skill.DEFENCE, Skill.HERBLORE, Skill.FARMING, Skill.WOODCUTTING,
		Skill.FISHING, Skill.COOKING, Skill.MINING, Skill.SMITHING
	});

	private final JComboBox<GearProgression.Style> gearStyleSelector = new JComboBox<>(GearProgression.Style.values());
	private final JComboBox<String> bossSelector = new JComboBox<>(new String[]{
		"Vorkath", "Zulrah", "The Corrupted Gauntlet (CG)", "Phantom Muspah",
		"Tombs of Amascut (ToA)", "Chambers of Xeric (CoX)", "Theatre of Blood (ToB)", "Grotesque Guardians"
	});

	private final Set<String> completedDailies = new HashSet<>();
	private Map<Skill, Integer> cachedSkills = new EnumMap<>(Skill.class);
	private int cachedCombatLevel = 3;

	public AstrariumPanel(AstrariumPlugin plugin, AstrariumConfig config)
	{
		super(false);
		this.plugin = plugin;
		this.config = config;

		setLayout(new BorderLayout());
		setBackground(ColorScheme.DARK_GRAY_COLOR);

		// Header Card with Theme Gradient
		headerCard = new JPanel()
		{
			@Override
			protected void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				AstrariumTheme theme = config.theme();
				GradientPaint paint = new GradientPaint(
					0, 0, theme.getHeaderGradientStart(),
					0, getHeight(), theme.getHeaderGradientEnd()
				);
				g2.setPaint(paint);
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
				g2.setColor(theme.getSecondaryColor());
				g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
				g2.dispose();
			}
		};
		headerCard.setLayout(new BoxLayout(headerCard, BoxLayout.Y_AXIS));
		headerCard.setBorder(new EmptyBorder(10, 10, 10, 10));
		headerCard.setOpaque(false);

		nameLabel = new JLabel("Astrarium Adventurer", SwingConstants.CENTER);
		nameLabel.setFont(FontManager.getRunescapeBoldFont());
		nameLabel.setForeground(Color.WHITE);
		nameLabel.setAlignmentX(CENTER_ALIGNMENT);

		combatLevelLabel = new JLabel("Combat Level: --", SwingConstants.CENTER);
		combatLevelLabel.setFont(FontManager.getRunescapeFont());
		combatLevelLabel.setForeground(config.theme().getPrimaryColor());
		combatLevelLabel.setAlignmentX(CENTER_ALIGNMENT);

		combatProgressBar = new JProgressBar(0, 100);
		combatProgressBar.setValue(0);
		combatProgressBar.setStringPainted(true);
		combatProgressBar.setString("0% to Next Combat");
		combatProgressBar.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
		combatProgressBar.setMaximumSize(new Dimension(200, 16));
		combatProgressBar.setAlignmentX(CENTER_ALIGNMENT);

		nextCombatMathLabel = new JLabel("Calculating next combat level...", SwingConstants.CENTER);
		nextCombatMathLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
		nextCombatMathLabel.setForeground(Color.LIGHT_GRAY);
		nextCombatMathLabel.setAlignmentX(CENTER_ALIGNMENT);

		totalLevelLabel = new JLabel("Total Level: --", SwingConstants.CENTER);
		totalLevelLabel.setFont(FontManager.getRunescapeSmallFont());
		totalLevelLabel.setForeground(Color.GRAY);
		totalLevelLabel.setAlignmentX(CENTER_ALIGNMENT);

		headerCard.add(nameLabel);
		headerCard.add(Box.createVerticalStrut(3));
		headerCard.add(combatLevelLabel);
		headerCard.add(Box.createVerticalStrut(5));
		headerCard.add(combatProgressBar);
		headerCard.add(Box.createVerticalStrut(4));
		headerCard.add(nextCombatMathLabel);
		headerCard.add(Box.createVerticalStrut(3));
		headerCard.add(totalLevelLabel);

		// Navigation Bar (2 rows of 3 buttons)
		JPanel navContainer = new JPanel();
		navContainer.setLayout(new BoxLayout(navContainer, BoxLayout.Y_AXIS));
		navContainer.setBorder(new EmptyBorder(6, 4, 6, 4));
		navContainer.setBackground(ColorScheme.DARKER_GRAY_COLOR);

		JPanel navRow1 = new JPanel(new GridLayout(1, 3, 3, 0));
		navRow1.setOpaque(false);
		JPanel navRow2 = new JPanel(new GridLayout(1, 3, 3, 0));
		navRow2.setOpaque(false);

		styleNavButton(radarNavBtn, true);
		styleNavButton(questsNavBtn, false);
		styleNavButton(diariesNavBtn, false);
		styleNavButton(combatNavBtn, false);
		styleNavButton(utilitiesNavBtn, false);
		styleNavButton(sessionNavBtn, false);

		radarNavBtn.addActionListener(e -> switchTab(TAB_RADAR, radarNavBtn));
		questsNavBtn.addActionListener(e -> switchTab(TAB_QUESTS, questsNavBtn));
		diariesNavBtn.addActionListener(e -> switchTab(TAB_DIARIES, diariesNavBtn));
		combatNavBtn.addActionListener(e -> switchTab(TAB_COMBAT, combatNavBtn));
		utilitiesNavBtn.addActionListener(e -> switchTab(TAB_UTILITIES, utilitiesNavBtn));
		sessionNavBtn.addActionListener(e -> switchTab(TAB_SESSION, sessionNavBtn));

		navRow1.add(radarNavBtn);
		navRow1.add(questsNavBtn);
		navRow1.add(diariesNavBtn);

		navRow2.add(combatNavBtn);
		navRow2.add(utilitiesNavBtn);
		navRow2.add(sessionNavBtn);

		navContainer.add(navRow1);
		navContainer.add(Box.createVerticalStrut(3));
		navContainer.add(navRow2);

		// Top Container
		JPanel topContainer = new JPanel();
		topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
		topContainer.setBackground(ColorScheme.DARK_GRAY_COLOR);
		topContainer.setBorder(new EmptyBorder(6, 6, 0, 6));
		topContainer.add(headerCard);
		topContainer.add(Box.createVerticalStrut(4));
		topContainer.add(navContainer);

		add(topContainer, BorderLayout.NORTH);

		// Content Panes
		setupContentPanel(radarContainer);
		setupContentPanel(questsContainer);
		setupContentPanel(diariesContainer);
		setupContentPanel(combatContainer);
		setupContentPanel(utilitiesContainer);
		setupContentPanel(sessionContainer);

		contentCards.add(createScrollPane(radarContainer), TAB_RADAR);
		contentCards.add(createScrollPane(questsContainer), TAB_QUESTS);
		contentCards.add(createScrollPane(diariesContainer), TAB_DIARIES);
		contentCards.add(createScrollPane(combatContainer), TAB_COMBAT);
		contentCards.add(createScrollPane(utilitiesContainer), TAB_UTILITIES);
		contentCards.add(createScrollPane(sessionContainer), TAB_SESSION);

		add(contentCards, BorderLayout.CENTER);

		// Listeners
		skillSelector.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		skillSelector.setForeground(Color.WHITE);
		skillSelector.setFocusable(false);
		skillSelector.addActionListener(e -> refreshRadarTab());

		gearStyleSelector.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		gearStyleSelector.setForeground(Color.WHITE);
		gearStyleSelector.setFocusable(false);
		gearStyleSelector.addActionListener(e -> refreshCombatTab(plugin.getClient()));

		bossSelector.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		bossSelector.setForeground(Color.WHITE);
		bossSelector.setFocusable(false);
		bossSelector.addActionListener(e -> refreshCombatTab(plugin.getClient()));
	}

	private void setupContentPanel(JPanel panel)
	{
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(ColorScheme.DARK_GRAY_COLOR);
		panel.setBorder(new EmptyBorder(6, 6, 6, 6));
	}

	private JScrollPane createScrollPane(JPanel content)
	{
		JScrollPane scroll = new JScrollPane(content);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setBorder(null);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		return scroll;
	}

	private void styleNavButton(JButton btn, boolean active)
	{
		btn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
		btn.setFocusPainted(false);
		btn.setForeground(active ? config.theme().getPrimaryColor() : Color.LIGHT_GRAY);
		btn.setBackground(active ? ColorScheme.DARK_GRAY_HOVER_COLOR : ColorScheme.DARKER_GRAY_COLOR);
		btn.setBorder(BorderFactory.createLineBorder(active ? config.theme().getSecondaryColor() : ColorScheme.DARKER_GRAY_HOVER_COLOR, 1));
	}

	private void switchTab(String tabName, JButton activeBtn)
	{
		cardLayout.show(contentCards, tabName);
		styleNavButton(radarNavBtn, activeBtn == radarNavBtn);
		styleNavButton(questsNavBtn, activeBtn == questsNavBtn);
		styleNavButton(diariesNavBtn, activeBtn == diariesNavBtn);
		styleNavButton(combatNavBtn, activeBtn == combatNavBtn);
		styleNavButton(utilitiesNavBtn, activeBtn == utilitiesNavBtn);
		styleNavButton(sessionNavBtn, activeBtn == sessionNavBtn);
	}

	public void updatePlayerData(Client client)
	{
		SwingUtilities.invokeLater(() ->
		{
			if (client.getLocalPlayer() != null && client.getLocalPlayer().getName() != null)
			{
				nameLabel.setText(client.getLocalPlayer().getName());
			}

			Map<Skill, Integer> skills = new EnumMap<>(Skill.class);
			for (Skill skill : Skill.values())
			{
				skills.put(skill, client.getRealSkillLevel(skill));
			}
			this.cachedSkills = skills;

			CombatLevelMath combat = new CombatLevelMath(
				skills.getOrDefault(Skill.ATTACK, 1),
				skills.getOrDefault(Skill.STRENGTH, 1),
				skills.getOrDefault(Skill.DEFENCE, 1),
				skills.getOrDefault(Skill.HITPOINTS, 10),
				skills.getOrDefault(Skill.PRAYER, 1),
				skills.getOrDefault(Skill.RANGED, 1),
				skills.getOrDefault(Skill.MAGIC, 1)
			);
			this.cachedCombatLevel = combat.getCombatLevel();

			combatLevelLabel.setText("Combat Level: " + combat.getCombatLevel());
			combatLevelLabel.setForeground(config.theme().getPrimaryColor());

			int progressPct = (int) (combat.getProgressToNext() * 100);
			combatProgressBar.setValue(progressPct);
			combatProgressBar.setForeground(config.theme().getProgressBarColor());

			if (combat.getCombatLevel() >= 126)
			{
				combatProgressBar.setString("126 Maxed");
				nextCombatMathLabel.setText("Max Combat Level Achieved!");
			}
			else
			{
				combatProgressBar.setString(progressPct + "% to Combat " + (combat.getCombatLevel() + 1));
				if (config.showCombatMath())
				{
					List<String> paths = new ArrayList<>();
					if (combat.getStrengthNeeded() > 0) paths.add("+" + combat.getStrengthNeeded() + " Str");
					if (combat.getAttackNeeded() > 0 && combat.getAttackNeeded() != combat.getStrengthNeeded()) paths.add("+" + combat.getAttackNeeded() + " Att");
					if (combat.getDefenceNeeded() > 0) paths.add("+" + combat.getDefenceNeeded() + " Def");
					if (combat.getPrayerNeeded() > 0) paths.add("+" + combat.getPrayerNeeded() + " Pray");
					if (combat.getRangedNeeded() > 0) paths.add("+" + combat.getRangedNeeded() + " Range");
					if (combat.getMagicNeeded() > 0) paths.add("+" + combat.getMagicNeeded() + " Mage");

					String mathText = paths.isEmpty() ? "Train combat skills to advance" : "Next: " + String.join(" | ", paths.subList(0, Math.min(paths.size(), 3)));
					nextCombatMathLabel.setText(mathText);
				}
				else
				{
					nextCombatMathLabel.setText("");
				}
			}

			totalLevelLabel.setText("Total Level: " + client.getTotalLevel());

			refreshRadarTab();
			refreshQuestsTab(client, skills);
			refreshDiariesTab(skills);
			refreshCombatTab(client);
			refreshUtilitiesTab();
			refreshSessionTab();

			revalidate();
			repaint();
		});
	}

	// 1. RADAR TAB (Skilling Leveling Guide + Milestone Progression)
	private void refreshRadarTab()
	{
		radarContainer.removeAll();

		// Skill Leveling Guide Selector
		JPanel selectorCard = new JPanel(new BorderLayout(4, 0));
		selectorCard.setBackground(config.theme().getCardBackground());
		selectorCard.setBorder(new EmptyBorder(6, 6, 6, 6));

		JLabel pickLabel = new JLabel("Training:");
		pickLabel.setFont(FontManager.getRunescapeBoldFont());
		pickLabel.setForeground(config.theme().getPrimaryColor());

		selectorCard.add(pickLabel, BorderLayout.WEST);
		selectorCard.add(skillSelector, BorderLayout.CENTER);

		radarContainer.add(selectorCard);
		radarContainer.add(Box.createVerticalStrut(6));

		Skill selectedSkill = (Skill) skillSelector.getSelectedItem();
		if (selectedSkill == null) selectedSkill = Skill.AGILITY;
		int level = cachedSkills.getOrDefault(selectedSkill, 1);

		OptimalLevelingGuide.TrainingMethod current = OptimalLevelingGuide.getOptimalMethod(selectedSkill, level);
		OptimalLevelingGuide.TrainingMethod next = OptimalLevelingGuide.getNextMethod(selectedSkill, level);

		if (current != null)
		{
			JPanel currentCard = new JPanel();
			currentCard.setLayout(new BoxLayout(currentCard, BoxLayout.Y_AXIS));
			currentCard.setBackground(config.theme().getCardBackground());
			currentCard.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(config.theme().getPrimaryColor(), 1),
				new EmptyBorder(8, 8, 8, 8)
			));

			JLabel curTitle = new JLabel("\uD83C\uDFAF Lvl " + level + " " + selectedSkill.getName() + " Optimal Method");
			curTitle.setFont(FontManager.getRunescapeBoldFont());
			curTitle.setForeground(config.theme().getPrimaryColor());

			JLabel methodName = new JLabel(current.getName());
			methodName.setFont(FontManager.getRunescapeBoldFont());
			methodName.setForeground(Color.WHITE);

			JLabel rate = new JLabel("\u26A1 " + current.getRate());
			rate.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
			rate.setForeground(new Color(100, 240, 120));

			JLabel loc = new JLabel("\uD83D\uDCCD " + current.getLocation());
			loc.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
			loc.setForeground(Color.LIGHT_GRAY);

			JLabel notes = new JLabel("<html><body style='width: 170px;'>\uD83D\uDCA1 " + current.getNotes() + "</body></html>");
			notes.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 9));
			notes.setForeground(Color.GRAY);

			currentCard.add(curTitle);
			currentCard.add(Box.createVerticalStrut(3));
			currentCard.add(methodName);
			currentCard.add(Box.createVerticalStrut(2));
			currentCard.add(rate);
			currentCard.add(Box.createVerticalStrut(2));
			currentCard.add(loc);
			currentCard.add(Box.createVerticalStrut(3));
			currentCard.add(notes);

			radarContainer.add(currentCard);
			radarContainer.add(Box.createVerticalStrut(6));
		}

		if (next != null)
		{
			JPanel nextCard = new JPanel();
			nextCard.setLayout(new BoxLayout(nextCard, BoxLayout.Y_AXIS));
			nextCard.setBackground(ColorScheme.DARKER_GRAY_COLOR);
			nextCard.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(ColorScheme.DARKER_GRAY_HOVER_COLOR, 1),
				new EmptyBorder(6, 6, 6, 6)
			));

			JLabel nextHeader = new JLabel("\uD83D\uDD13 Next Unlock (Level " + next.getMinLevel() + ")");
			nextHeader.setFont(FontManager.getRunescapeBoldFont());
			nextHeader.setForeground(new Color(255, 185, 80));

			JLabel nextName = new JLabel(next.getName() + " (" + next.getRate() + ")");
			nextName.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
			nextName.setForeground(Color.LIGHT_GRAY);

			nextCard.add(nextHeader);
			nextCard.add(Box.createVerticalStrut(2));
			nextCard.add(nextName);

			radarContainer.add(nextCard);
			radarContainer.add(Box.createVerticalStrut(10));
		}

		// Account Milestones Section
		JLabel milestoneHeading = new JLabel("\uD83C\uDFC5 Account Milestone Radar");
		milestoneHeading.setFont(FontManager.getRunescapeBoldFont());
		milestoneHeading.setForeground(config.theme().getPrimaryColor());
		radarContainer.add(milestoneHeading);
		radarContainer.add(Box.createVerticalStrut(6));

		for (Milestone milestone : MilestoneRepository.getMilestones())
		{
			JPanel card = createMilestoneCard(milestone, cachedSkills);
			radarContainer.add(card);
			radarContainer.add(Box.createVerticalStrut(6));
		}
	}

	private JPanel createMilestoneCard(Milestone milestone, Map<Skill, Integer> skills)
	{
		JPanel card = new JPanel();
		card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
		card.setBackground(config.theme().getCardBackground());
		card.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createLineBorder(milestone.isCompleted(skills) ? new Color(50, 180, 80) : ColorScheme.DARKER_GRAY_HOVER_COLOR, 1),
			new EmptyBorder(8, 8, 8, 8)
		));

		JLabel title = new JLabel((milestone.isCompleted(skills) ? "\u2705 " : "\uD83D\uDD12 ") + milestone.getTitle());
		title.setFont(FontManager.getRunescapeBoldFont());
		title.setForeground(milestone.isCompleted(skills) ? Color.WHITE : config.theme().getPrimaryColor());

		JLabel desc = new JLabel("<html><body style='width: 170px;'>" + milestone.getUnlockDescription() + "</body></html>");
		desc.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
		desc.setForeground(Color.LIGHT_GRAY);

		double pct = milestone.getCompletionPercentage(skills);
		JProgressBar bar = new JProgressBar(0, 100);
		bar.setValue((int) pct);
		bar.setStringPainted(true);
		bar.setString(String.format("%.0f%% Complete", pct));
		bar.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 9));
		bar.setForeground(milestone.isCompleted(skills) ? new Color(50, 180, 80) : config.theme().getProgressBarColor());

		card.add(title);
		card.add(Box.createVerticalStrut(2));
		card.add(desc);
		card.add(Box.createVerticalStrut(4));
		card.add(bar);

		return card;
	}

	// 2. QUESTS TAB
	private void refreshQuestsTab(Client client, Map<Skill, Integer> skills)
	{
		questsContainer.removeAll();

		List<OptimalQuestGuide.QuestEntry> quests = OptimalQuestGuide.getOptimalQuests();
		int completedCount = 0;
		for (OptimalQuestGuide.QuestEntry q : quests)
		{
			if (q.getState(client) == QuestState.FINISHED) completedCount++;
		}

		JPanel banner = new JPanel(new BorderLayout());
		banner.setBackground(config.theme().getHeaderGradientEnd());
		banner.setBorder(new EmptyBorder(8, 8, 8, 8));
		JLabel bannerTitle = new JLabel("Optimal Quest Route (" + completedCount + "/" + quests.size() + ")", SwingConstants.CENTER);
		bannerTitle.setFont(FontManager.getRunescapeBoldFont());
		bannerTitle.setForeground(config.theme().getPrimaryColor());
		banner.add(bannerTitle, BorderLayout.CENTER);

		questsContainer.add(banner);
		questsContainer.add(Box.createVerticalStrut(6));

		for (OptimalQuestGuide.QuestEntry q : quests)
		{
			QuestState state = q.getState(client);
			boolean isFinished = state == QuestState.FINISHED;
			boolean isInProgress = state == QuestState.IN_PROGRESS;
			boolean hasSkills = q.meetsSkillRequirements(skills);

			JPanel card = new JPanel();
			card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
			card.setBackground(config.theme().getCardBackground());

			Color borderColor = isFinished ? new Color(50, 180, 80)
				: isInProgress ? new Color(220, 160, 40)
				: hasSkills ? config.theme().getSecondaryColor()
				: ColorScheme.DARKER_GRAY_HOVER_COLOR;

			card.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(borderColor, 1),
				new EmptyBorder(6, 6, 6, 6)
			));

			String icon = isFinished ? "\u2705 " : isInProgress ? "\u25B6\uFE0F " : hasSkills ? "\uD83D\uDD13 " : "\uD83D\uDD12 ";
			JLabel title = new JLabel(icon + q.getTitle() + " (" + q.getQuestPoints() + " QP)");
			title.setFont(FontManager.getRunescapeBoldFont());
			title.setForeground(isFinished ? Color.WHITE : config.theme().getPrimaryColor());

			JLabel reward = new JLabel("<html><body style='width: 170px;'>\u2B50 " + q.getRewardHighlight() + "</body></html>");
			reward.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
			reward.setForeground(Color.LIGHT_GRAY);

			JLabel reqStatus = new JLabel(isFinished ? "Completed" : isInProgress ? "In Progress" : q.getMissingSkillsString(skills));
			reqStatus.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 9));
			reqStatus.setForeground(isFinished ? new Color(100, 240, 120) : hasSkills ? new Color(255, 200, 80) : new Color(230, 80, 80));

			card.add(title);
			card.add(Box.createVerticalStrut(2));
			card.add(reward);
			card.add(Box.createVerticalStrut(3));
			card.add(reqStatus);

			questsContainer.add(card);
			questsContainer.add(Box.createVerticalStrut(4));
		}
	}

	// 3. DIARIES TAB
	private void refreshDiariesTab(Map<Skill, Integer> skills)
	{
		diariesContainer.removeAll();

		JLabel heading = new JLabel("\uD83D\uDCDC High-Impact Diary Perks");
		heading.setFont(FontManager.getRunescapeBoldFont());
		heading.setForeground(config.theme().getPrimaryColor());
		diariesContainer.add(heading);
		diariesContainer.add(Box.createVerticalStrut(6));

		for (DiaryRepository.DiaryEntry diary : DiaryRepository.getDiaries())
		{
			boolean eligible = diary.meetsRequirements(skills);

			JPanel card = new JPanel();
			card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
			card.setBackground(config.theme().getCardBackground());
			card.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(eligible ? new Color(50, 180, 80) : ColorScheme.DARKER_GRAY_HOVER_COLOR, 1),
				new EmptyBorder(6, 6, 6, 6)
			));

			JLabel title = new JLabel((eligible ? "\u2705 " : "\uD83D\uDD12 ") + diary.getRegion() + " (" + diary.getTier() + ")");
			title.setFont(FontManager.getRunescapeBoldFont());
			title.setForeground(eligible ? Color.WHITE : config.theme().getPrimaryColor());

			JLabel reward = new JLabel("<html><body style='width: 170px;'>\uD83C\uDFC6 " + diary.getRewardHighlights() + "</body></html>");
			reward.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
			reward.setForeground(Color.LIGHT_GRAY);

			JLabel reqs = new JLabel(diary.getMissingSkillsString(skills));
			reqs.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 9));
			reqs.setForeground(eligible ? new Color(100, 240, 120) : new Color(230, 80, 80));

			card.add(title);
			card.add(Box.createVerticalStrut(2));
			card.add(reward);
			card.add(Box.createVerticalStrut(3));
			card.add(reqs);

			diariesContainer.add(card);
			diariesContainer.add(Box.createVerticalStrut(4));
		}
	}

	// 4. COMBAT & PVM TAB (Gear Upgrades, Slayer Audit, Boss Intel)
	private void refreshCombatTab(Client client)
	{
		combatContainer.removeAll();

		// Slayer gear readiness banner
		List<ActivityReadiness.ReadinessWarning> warnings = ActivityReadiness.auditInventory(client, plugin.getCurrentSlayerTask());
		JPanel statusBanner = new JPanel(new BorderLayout());
		statusBanner.setBorder(new EmptyBorder(6, 6, 6, 6));

		if (warnings.isEmpty())
		{
			statusBanner.setBackground(new Color(25, 45, 25));
			JLabel ok = new JLabel("\u2705 Slayer Gear Ready", SwingConstants.CENTER);
			ok.setFont(FontManager.getRunescapeBoldFont());
			ok.setForeground(new Color(100, 240, 120));
			statusBanner.add(ok, BorderLayout.CENTER);
		}
		else
		{
			statusBanner.setBackground(new Color(55, 25, 25));
			JLabel warn = new JLabel("\u26A0\uFE0F Missing Gear Alert (" + warnings.size() + ")", SwingConstants.CENTER);
			warn.setFont(FontManager.getRunescapeBoldFont());
			warn.setForeground(new Color(255, 90, 90));
			statusBanner.add(warn, BorderLayout.CENTER);
		}

		combatContainer.add(statusBanner);
		combatContainer.add(Box.createVerticalStrut(4));

		for (ActivityReadiness.ReadinessWarning w : warnings)
		{
			JPanel card = new JPanel();
			card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
			card.setBackground(config.theme().getCardBackground());
			card.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(w.isSevere() ? new Color(220, 50, 50) : ColorScheme.DARKER_GRAY_HOVER_COLOR, 1),
				new EmptyBorder(6, 6, 6, 6)
			));

			JLabel title = new JLabel(w.getTitle());
			title.setFont(FontManager.getRunescapeBoldFont());
			title.setForeground(Color.WHITE);

			JLabel desc = new JLabel("<html><body style='width: 170px;'>" + w.getDescription() + "</body></html>");
			desc.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
			desc.setForeground(Color.LIGHT_GRAY);

			card.add(title);
			card.add(Box.createVerticalStrut(2));
			card.add(desc);

			combatContainer.add(card);
			combatContainer.add(Box.createVerticalStrut(4));
		}

		combatContainer.add(Box.createVerticalStrut(6));

		// Boss Strategy Cheatsheet Section
		JPanel bossCard = new JPanel(new BorderLayout(4, 0));
		bossCard.setBackground(config.theme().getCardBackground());
		bossCard.setBorder(new EmptyBorder(6, 6, 6, 6));

		JLabel bossLabel = new JLabel("Boss Intel:");
		bossLabel.setFont(FontManager.getRunescapeBoldFont());
		bossLabel.setForeground(config.theme().getPrimaryColor());

		bossCard.add(bossLabel, BorderLayout.WEST);
		bossCard.add(bossSelector, BorderLayout.CENTER);

		combatContainer.add(bossCard);
		combatContainer.add(Box.createVerticalStrut(4));

		String selectedBoss = (String) bossSelector.getSelectedItem();
		for (BossIntel.BossGuide guide : BossIntel.getGuides())
		{
			if (guide.getBossName().equalsIgnoreCase(selectedBoss))
			{
				JPanel gCard = new JPanel();
				gCard.setLayout(new BoxLayout(gCard, BoxLayout.Y_AXIS));
				gCard.setBackground(config.theme().getCardBackground());
				gCard.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(config.theme().getPrimaryColor(), 1),
					new EmptyBorder(6, 6, 6, 6)
				));

				JLabel diff = new JLabel("\uD83C\uDFC6 " + guide.getDifficulty());
				diff.setFont(FontManager.getRunescapeBoldFont());
				diff.setForeground(config.theme().getPrimaryColor());

				JLabel pray = new JLabel("<html><body style='width: 170px;'>\uD83D\uDEE1\uFE0F <b>Overhead:</b> " + guide.getOverheadPrayers() + "</body></html>");
				pray.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
				pray.setForeground(Color.WHITE);

				JLabel items = new JLabel("<html><body style='width: 170px;'>\uD83C\uDF92 <b>Essentials:</b> " + guide.getEssentialItems() + "</body></html>");
				items.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 9));
				items.setForeground(new Color(100, 240, 120));

				JLabel mech = new JLabel("<html><body style='width: 170px;'>\u26A1 <b>Tactics:</b> " + guide.getCoreMechanics() + "</body></html>");
				mech.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 9));
				mech.setForeground(Color.LIGHT_GRAY);

				gCard.add(diff);
				gCard.add(Box.createVerticalStrut(3));
				gCard.add(pray);
				gCard.add(Box.createVerticalStrut(2));
				gCard.add(items);
				gCard.add(Box.createVerticalStrut(2));
				gCard.add(mech);

				combatContainer.add(gCard);
				break;
			}
		}

		combatContainer.add(Box.createVerticalStrut(8));

		// Combat Gear Upgrades Section
		JPanel styleCard = new JPanel(new BorderLayout(4, 0));
		styleCard.setBackground(config.theme().getCardBackground());
		styleCard.setBorder(new EmptyBorder(6, 6, 6, 6));

		JLabel styleLabel = new JLabel("Gear Ladder:");
		styleLabel.setFont(FontManager.getRunescapeBoldFont());
		styleLabel.setForeground(config.theme().getPrimaryColor());

		styleCard.add(styleLabel, BorderLayout.WEST);
		styleCard.add(gearStyleSelector, BorderLayout.CENTER);

		combatContainer.add(styleCard);
		combatContainer.add(Box.createVerticalStrut(6));

		GearProgression.Style selectedStyle = (GearProgression.Style) gearStyleSelector.getSelectedItem();
		if (selectedStyle == null) selectedStyle = GearProgression.Style.MELEE;

		for (GearProgression.GearUpgrade up : GearProgression.getUpgrades(selectedStyle))
		{
			JPanel upCard = new JPanel();
			upCard.setLayout(new BoxLayout(upCard, BoxLayout.Y_AXIS));
			upCard.setBackground(config.theme().getCardBackground());
			upCard.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(config.theme().getSecondaryColor(), 1),
				new EmptyBorder(6, 6, 6, 6)
			));

			JLabel title = new JLabel("[Tier " + up.getTier() + " " + up.getSlot() + "] " + up.getName());
			title.setFont(FontManager.getRunescapeBoldFont());
			title.setForeground(Color.WHITE);

			JLabel bonus = new JLabel("<html><body style='width: 170px;'>\u26A1 " + up.getBonusDescription() + "</body></html>");
			bonus.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
			bonus.setForeground(new Color(100, 240, 120));

			JLabel src = new JLabel("<html><body style='width: 170px;'>\uD83D\uDCCD " + up.getRequirementOrSource() + "</body></html>");
			src.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 9));
			src.setForeground(Color.LIGHT_GRAY);

			upCard.add(title);
			upCard.add(Box.createVerticalStrut(2));
			upCard.add(bonus);
			upCard.add(Box.createVerticalStrut(2));
			upCard.add(src);

			combatContainer.add(upCard);
			combatContainer.add(Box.createVerticalStrut(4));
		}
	}

	// 5. UTILITIES TAB (Dailies Checklist + Herb Patch Navigator)
	private void refreshUtilitiesTab()
	{
		utilitiesContainer.removeAll();

		JLabel heading = new JLabel("\u2600\uFE0F Daily & Weekly Resets");
		heading.setFont(FontManager.getRunescapeBoldFont());
		heading.setForeground(config.theme().getPrimaryColor());
		utilitiesContainer.add(heading);
		utilitiesContainer.add(Box.createVerticalStrut(6));

		for (DailiesTracker.DailyActivity act : DailiesTracker.getActivities())
		{
			JPanel card = new JPanel(new BorderLayout(4, 0));
			card.setBackground(config.theme().getCardBackground());
			card.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(ColorScheme.DARKER_GRAY_HOVER_COLOR, 1),
				new EmptyBorder(6, 6, 6, 6)
			));

			JCheckBox box = new JCheckBox();
			box.setSelected(completedDailies.contains(act.getId()));
			box.setOpaque(false);
			box.addActionListener(e ->
			{
				if (box.isSelected()) completedDailies.add(act.getId());
				else completedDailies.remove(act.getId());
			});

			JPanel text = new JPanel();
			text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
			text.setOpaque(false);

			JLabel name = new JLabel(act.getName());
			name.setFont(FontManager.getRunescapeBoldFont());
			name.setForeground(Color.WHITE);

			JLabel reward = new JLabel("<html><body style='width: 155px;'>\uD83D\uDCB0 " + act.getRewardDescription() + "</body></html>");
			reward.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
			reward.setForeground(config.theme().getPrimaryColor());

			JLabel loc = new JLabel("<html><body style='width: 155px;'>\uD83D\uDCCD " + act.getLocation() + "</body></html>");
			loc.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 9));
			loc.setForeground(Color.GRAY);

			text.add(name);
			text.add(Box.createVerticalStrut(2));
			text.add(reward);
			text.add(Box.createVerticalStrut(2));
			text.add(loc);

			card.add(box, BorderLayout.WEST);
			card.add(text, BorderLayout.CENTER);

			utilitiesContainer.add(card);
			utilitiesContainer.add(Box.createVerticalStrut(4));
		}

		utilitiesContainer.add(Box.createVerticalStrut(8));

		// Herb Patches Section
		JLabel herbHead = new JLabel("\uD83C\uDF3E All 9 Herb Patches & Teleports");
		herbHead.setFont(FontManager.getRunescapeBoldFont());
		herbHead.setForeground(config.theme().getPrimaryColor());
		utilitiesContainer.add(herbHead);
		utilitiesContainer.add(Box.createVerticalStrut(6));

		for (FarmingPatches.HerbPatch patch : FarmingPatches.getPatches())
		{
			JPanel pCard = new JPanel();
			pCard.setLayout(new BoxLayout(pCard, BoxLayout.Y_AXIS));
			pCard.setBackground(config.theme().getCardBackground());
			pCard.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(patch.isDiseaseFree() ? new Color(50, 180, 80) : ColorScheme.DARKER_GRAY_HOVER_COLOR, 1),
				new EmptyBorder(6, 6, 6, 6)
			));

			String tag = patch.isDiseaseFree() ? "\u2705 " : "\uD83C\uDF3F ";
			JLabel pTitle = new JLabel(tag + patch.getLocation() + (patch.isDiseaseFree() ? " (Disease-Free)" : ""));
			pTitle.setFont(FontManager.getRunescapeBoldFont());
			pTitle.setForeground(patch.isDiseaseFree() ? new Color(100, 240, 120) : Color.WHITE);

			JLabel tele = new JLabel("<html><body style='width: 170px;'>\uD83D\uDE80 <b>Teleport:</b> " + patch.getTeleportMethod() + "</body></html>");
			tele.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
			tele.setForeground(Color.LIGHT_GRAY);

			JLabel perk = new JLabel("<html><body style='width: 170px;'>\u2B50 " + patch.getDiaryBonus() + "</body></html>");
			perk.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 9));
			perk.setForeground(config.theme().getPrimaryColor());

			pCard.add(pTitle);
			pCard.add(Box.createVerticalStrut(2));
			pCard.add(tele);
			pCard.add(Box.createVerticalStrut(2));
			pCard.add(perk);

			utilitiesContainer.add(pCard);
			utilitiesContainer.add(Box.createVerticalStrut(4));
		}
	}

	// 6. SESSION, UNLOCKS & WILDY TAB
	private void refreshSessionTab()
	{
		sessionContainer.removeAll();

		SessionTracker session = plugin.getSessionTracker();

		// Session Stats Card
		JPanel statsCard = new JPanel();
		statsCard.setLayout(new BoxLayout(statsCard, BoxLayout.Y_AXIS));
		statsCard.setBackground(config.theme().getCardBackground());
		statsCard.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createLineBorder(config.theme().getPrimaryColor(), 1),
			new EmptyBorder(8, 8, 8, 8)
		));

		JLabel sessionHead = new JLabel("\u23F1\uFE0F Session Time: " + session.getSessionDurationString());
		sessionHead.setFont(FontManager.getRunescapeBoldFont());
		sessionHead.setForeground(Color.WHITE);

		JLabel xpLabel = new JLabel(String.format("\uD83C\uDF1F Gained: %,d XP (%,d XP/hr)", session.getTotalXpGained(), session.getXpPerHour()));
		xpLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
		xpLabel.setForeground(new Color(100, 240, 120));

		JLabel statsLine = new JLabel("⚔️ Bosses: " + session.getBossKillsCount() + " | 📜 Clues: " + session.getCluesCount());
		statsLine.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
		statsLine.setForeground(Color.LIGHT_GRAY);

		JButton discordBtn = new JButton("\uD83D\uDCE4 Share Card to Discord");
		discordBtn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
		discordBtn.setFocusPainted(false);
		discordBtn.setBackground(new Color(88, 101, 242));
		discordBtn.setForeground(Color.WHITE);
		discordBtn.setAlignmentX(CENTER_ALIGNMENT);
		discordBtn.addActionListener(e -> plugin.exportSessionToDiscord(cachedCombatLevel));

		statsCard.add(sessionHead);
		statsCard.add(Box.createVerticalStrut(3));
		statsCard.add(xpLabel);
		statsCard.add(Box.createVerticalStrut(3));
		statsCard.add(statsLine);
		statsCard.add(Box.createVerticalStrut(6));
		statsCard.add(discordBtn);

		sessionContainer.add(statsCard);
		sessionContainer.add(Box.createVerticalStrut(8));

		// Wilderness Survival Radar Section
		JLabel wildyHead = new JLabel("\u26A0\uFE0F Wilderness Survival Rules");
		wildyHead.setFont(FontManager.getRunescapeBoldFont());
		wildyHead.setForeground(new Color(255, 90, 90));
		sessionContainer.add(wildyHead);
		sessionContainer.add(Box.createVerticalStrut(4));

		for (WildernessSurvival.SurvivalTip tip : WildernessSurvival.getTips())
		{
			JPanel wCard = new JPanel();
			wCard.setLayout(new BoxLayout(wCard, BoxLayout.Y_AXIS));
			wCard.setBackground(config.theme().getCardBackground());
			wCard.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(ColorScheme.DARKER_GRAY_HOVER_COLOR, 1),
				new EmptyBorder(6, 6, 6, 6)
			));

			JLabel wTitle = new JLabel("[" + tip.getRuleLevel() + "] " + tip.getTitle());
			wTitle.setFont(FontManager.getRunescapeBoldFont());
			wTitle.setForeground(new Color(255, 120, 120));

			JLabel wDesc = new JLabel("<html><body style='width: 170px;'>" + tip.getDescription() + "</body></html>");
			wDesc.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 9));
			wDesc.setForeground(Color.LIGHT_GRAY);

			wCard.add(wTitle);
			wCard.add(Box.createVerticalStrut(2));
			wCard.add(wDesc);

			sessionContainer.add(wCard);
			sessionContainer.add(Box.createVerticalStrut(4));
		}

		sessionContainer.add(Box.createVerticalStrut(8));

		// Transportation & Spellbook Unlocks Matrix Section
		JLabel unlockHead = new JLabel("\uD83C\uDF10 Transport & Spellbook Matrix");
		unlockHead.setFont(FontManager.getRunescapeBoldFont());
		unlockHead.setForeground(config.theme().getPrimaryColor());
		sessionContainer.add(unlockHead);
		sessionContainer.add(Box.createVerticalStrut(4));

		for (UnlockMatrix.UnlockEntry un : UnlockMatrix.getUnlocks(null))
		{
			JPanel uCard = new JPanel();
			uCard.setLayout(new BoxLayout(uCard, BoxLayout.Y_AXIS));
			uCard.setBackground(config.theme().getCardBackground());
			uCard.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(ColorScheme.DARKER_GRAY_HOVER_COLOR, 1),
				new EmptyBorder(6, 6, 6, 6)
			));

			JLabel uTitle = new JLabel("[" + un.getCategory() + "] " + un.getName());
			uTitle.setFont(FontManager.getRunescapeBoldFont());
			uTitle.setForeground(Color.WHITE);

			JLabel uReq = new JLabel("<html><body style='width: 170px;'>\uD83D\uDD12 " + un.getRequirement() + "</body></html>");
			uReq.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 9));
			uReq.setForeground(config.theme().getPrimaryColor());

			JLabel uDesc = new JLabel("<html><body style='width: 170px;'>\u2B50 " + un.getImpactDescription() + "</body></html>");
			uDesc.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 9));
			uDesc.setForeground(Color.LIGHT_GRAY);

			uCard.add(uTitle);
			uCard.add(Box.createVerticalStrut(2));
			uCard.add(uReq);
			uCard.add(Box.createVerticalStrut(2));
			uCard.add(uDesc);

			sessionContainer.add(uCard);
			sessionContainer.add(Box.createVerticalStrut(4));
		}

		sessionContainer.add(Box.createVerticalStrut(8));

		// Live Timeline Items Section
		JLabel timeHead = new JLabel("\uD83D\uDCDC Live Session Timeline");
		timeHead.setFont(FontManager.getRunescapeBoldFont());
		timeHead.setForeground(config.theme().getPrimaryColor());
		sessionContainer.add(timeHead);
		sessionContainer.add(Box.createVerticalStrut(4));

		List<AstrariumEvent> events = plugin.getTimelineEvents();
		if (events.isEmpty())
		{
			JLabel empty = new JLabel("<html><center>No events recorded this session.<br>Slay bosses, level up, or solve clues!</center></html>", SwingConstants.CENTER);
			empty.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 11));
			empty.setForeground(Color.GRAY);
			empty.setBorder(new EmptyBorder(15, 10, 10, 10));
			sessionContainer.add(empty);
			return;
		}

		for (AstrariumEvent ev : events)
		{
			JPanel entry = new JPanel(new BorderLayout(6, 0));
			entry.setBackground(config.theme().getCardBackground());
			entry.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(ColorScheme.DARKER_GRAY_HOVER_COLOR, 1),
				new EmptyBorder(6, 6, 6, 6)
			));

			JLabel badge = new JLabel(ev.getType().getEmoji());
			badge.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));

			JPanel textPanel = new JPanel();
			textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
			textPanel.setOpaque(false);

			JLabel head = new JLabel(ev.getHeadline());
			head.setFont(FontManager.getRunescapeBoldFont());
			head.setForeground(Color.WHITE);

			JLabel sub = new JLabel(ev.getTimestamp() + " \u2022 " + ev.getSubtext());
			sub.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 9));
			sub.setForeground(config.theme().getPrimaryColor());

			textPanel.add(head);
			textPanel.add(sub);

			entry.add(badge, BorderLayout.WEST);
			entry.add(textPanel, BorderLayout.CENTER);

			sessionContainer.add(entry);
			sessionContainer.add(Box.createVerticalStrut(4));
		}
	}
}
