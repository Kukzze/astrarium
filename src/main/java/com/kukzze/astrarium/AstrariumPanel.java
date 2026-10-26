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
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
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
	private static final String TAB_ROADMAP = "ROADMAP";
	private static final String TAB_QUESTS = "QUESTS";
	private static final String TAB_LEVELING = "LEVELING";
	private static final String TAB_READINESS = "READINESS";
	private static final String TAB_TIMELINE = "TIMELINE";

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

	private final JPanel roadmapContainer = new JPanel();
	private final JPanel questsContainer = new JPanel();
	private final JPanel levelingContainer = new JPanel();
	private final JPanel readinessContainer = new JPanel();
	private final JPanel timelineContainer = new JPanel();

	private final JButton roadmapNavBtn = new JButton("\uD83E\uDDED Radar");
	private final JButton questsNavBtn = new JButton("\uD83D\uDDFA\uFE0F Quests");
	private final JButton levelingNavBtn = new JButton("\u26A1 Train");
	private final JButton readinessNavBtn = new JButton("\uD83C\uDF92 Gear");
	private final JButton timelineNavBtn = new JButton("\uD83D\uDCDC Log");

	private final JComboBox<Skill> skillSelector = new JComboBox<>(new Skill[]{
		Skill.AGILITY, Skill.THIEVING, Skill.PRAYER, Skill.CONSTRUCTION,
		Skill.HUNTER, Skill.MAGIC, Skill.RANGED, Skill.ATTACK, Skill.STRENGTH,
		Skill.DEFENCE, Skill.HERBLORE, Skill.FARMING, Skill.WOODCUTTING,
		Skill.FISHING, Skill.COOKING, Skill.MINING, Skill.SMITHING
	});

	private Map<Skill, Integer> cachedSkills = new EnumMap<>(Skill.class);

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

		// Navigation Bar (2 rows for comfortable clickable touch on sidebar)
		JPanel navContainer = new JPanel();
		navContainer.setLayout(new BoxLayout(navContainer, BoxLayout.Y_AXIS));
		navContainer.setBorder(new EmptyBorder(6, 4, 6, 4));
		navContainer.setBackground(ColorScheme.DARKER_GRAY_COLOR);

		JPanel navRow1 = new JPanel(new GridLayout(1, 3, 3, 0));
		navRow1.setOpaque(false);
		JPanel navRow2 = new JPanel(new GridLayout(1, 2, 3, 0));
		navRow2.setOpaque(false);

		styleNavButton(roadmapNavBtn, true);
		styleNavButton(questsNavBtn, false);
		styleNavButton(levelingNavBtn, false);
		styleNavButton(readinessNavBtn, false);
		styleNavButton(timelineNavBtn, false);

		roadmapNavBtn.addActionListener(e -> switchTab(TAB_ROADMAP, roadmapNavBtn));
		questsNavBtn.addActionListener(e -> switchTab(TAB_QUESTS, questsNavBtn));
		levelingNavBtn.addActionListener(e -> switchTab(TAB_LEVELING, levelingNavBtn));
		readinessNavBtn.addActionListener(e -> switchTab(TAB_READINESS, readinessNavBtn));
		timelineNavBtn.addActionListener(e -> switchTab(TAB_TIMELINE, timelineNavBtn));

		navRow1.add(roadmapNavBtn);
		navRow1.add(questsNavBtn);
		navRow1.add(levelingNavBtn);

		navRow2.add(readinessNavBtn);
		navRow2.add(timelineNavBtn);

		navContainer.add(navRow1);
		navContainer.add(Box.createVerticalStrut(3));
		navContainer.add(navRow2);

		// Container for Top elements
		JPanel topContainer = new JPanel();
		topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
		topContainer.setBackground(ColorScheme.DARK_GRAY_COLOR);
		topContainer.setBorder(new EmptyBorder(6, 6, 0, 6));
		topContainer.add(headerCard);
		topContainer.add(Box.createVerticalStrut(4));
		topContainer.add(navContainer);

		add(topContainer, BorderLayout.NORTH);

		// Content Panes
		setupContentPanel(roadmapContainer);
		setupContentPanel(questsContainer);
		setupContentPanel(levelingContainer);
		setupContentPanel(readinessContainer);
		setupContentPanel(timelineContainer);

		contentCards.add(createScrollPane(roadmapContainer), TAB_ROADMAP);
		contentCards.add(createScrollPane(questsContainer), TAB_QUESTS);
		contentCards.add(createScrollPane(levelingContainer), TAB_LEVELING);
		contentCards.add(createScrollPane(readinessContainer), TAB_READINESS);
		contentCards.add(createScrollPane(timelineContainer), TAB_TIMELINE);

		add(contentCards, BorderLayout.CENTER);

		// Skill dropdown listener for leveling tab
		skillSelector.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		skillSelector.setForeground(Color.WHITE);
		skillSelector.setFocusable(false);
		skillSelector.addActionListener(e -> refreshLeveling());
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
		styleNavButton(roadmapNavBtn, activeBtn == roadmapNavBtn);
		styleNavButton(questsNavBtn, activeBtn == questsNavBtn);
		styleNavButton(levelingNavBtn, activeBtn == levelingNavBtn);
		styleNavButton(readinessNavBtn, activeBtn == readinessNavBtn);
		styleNavButton(timelineNavBtn, activeBtn == timelineNavBtn);
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

			refreshRoadmap(skills);
			refreshQuests(client, skills);
			refreshLeveling();
			refreshReadiness(client);
			refreshTimeline(plugin.getTimelineEvents());

			revalidate();
			repaint();
		});
	}

	private void refreshRoadmap(Map<Skill, Integer> skills)
	{
		roadmapContainer.removeAll();

		for (Milestone milestone : MilestoneRepository.getMilestones())
		{
			JPanel card = createMilestoneCard(milestone, skills);
			roadmapContainer.add(card);
			roadmapContainer.add(Box.createVerticalStrut(6));
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

	private void refreshQuests(Client client, Map<Skill, Integer> skills)
	{
		questsContainer.removeAll();

		List<OptimalQuestGuide.QuestEntry> quests = OptimalQuestGuide.getOptimalQuests();
		int completedCount = 0;

		for (OptimalQuestGuide.QuestEntry q : quests)
		{
			if (q.getState(client) == QuestState.FINISHED)
			{
				completedCount++;
			}
		}

		// Quest Progress Banner
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

	private void refreshLeveling()
	{
		levelingContainer.removeAll();

		// Skill picker dropdown header
		JPanel selectorCard = new JPanel(new BorderLayout(4, 0));
		selectorCard.setBackground(config.theme().getCardBackground());
		selectorCard.setBorder(new EmptyBorder(6, 6, 6, 6));

		JLabel pickLabel = new JLabel("Select Skill:");
		pickLabel.setFont(FontManager.getRunescapeBoldFont());
		pickLabel.setForeground(config.theme().getPrimaryColor());

		selectorCard.add(pickLabel, BorderLayout.WEST);
		selectorCard.add(skillSelector, BorderLayout.CENTER);

		levelingContainer.add(selectorCard);
		levelingContainer.add(Box.createVerticalStrut(8));

		Skill selectedSkill = (Skill) skillSelector.getSelectedItem();
		if (selectedSkill == null)
		{
			selectedSkill = Skill.AGILITY;
		}

		int level = cachedSkills.getOrDefault(selectedSkill, 1);

		OptimalLevelingGuide.TrainingMethod current = OptimalLevelingGuide.getOptimalMethod(selectedSkill, level);
		OptimalLevelingGuide.TrainingMethod next = OptimalLevelingGuide.getNextMethod(selectedSkill, level);

		// Current optimal method card
		if (current != null)
		{
			JPanel currentCard = new JPanel();
			currentCard.setLayout(new BoxLayout(currentCard, BoxLayout.Y_AXIS));
			currentCard.setBackground(config.theme().getCardBackground());
			currentCard.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(config.theme().getPrimaryColor(), 1),
				new EmptyBorder(8, 8, 8, 8)
			));

			JLabel curTitle = new JLabel("\uD83C\uDFAF Level " + level + " Optimal Method");
			curTitle.setFont(FontManager.getRunescapeBoldFont());
			curTitle.setForeground(config.theme().getPrimaryColor());

			JLabel methodName = new JLabel(current.getName());
			methodName.setFont(FontManager.getRunescapeBoldFont());
			methodName.setForeground(Color.WHITE);

			JLabel rate = new JLabel("\u26A1 Rate: " + current.getRate());
			rate.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
			rate.setForeground(new Color(100, 240, 120));

			JLabel loc = new JLabel("\uD83D\uDCCD " + current.getLocation());
			loc.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
			loc.setForeground(Color.LIGHT_GRAY);

			JLabel notes = new JLabel("<html><body style='width: 170px;'>\uD83D\uDCA1 " + current.getNotes() + "</body></html>");
			notes.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 9));
			notes.setForeground(Color.GRAY);

			currentCard.add(curTitle);
			currentCard.add(Box.createVerticalStrut(4));
			currentCard.add(methodName);
			currentCard.add(Box.createVerticalStrut(2));
			currentCard.add(rate);
			currentCard.add(Box.createVerticalStrut(2));
			currentCard.add(loc);
			currentCard.add(Box.createVerticalStrut(4));
			currentCard.add(notes);

			levelingContainer.add(currentCard);
			levelingContainer.add(Box.createVerticalStrut(8));
		}

		// Next unlock preview card
		if (next != null)
		{
			JPanel nextCard = new JPanel();
			nextCard.setLayout(new BoxLayout(nextCard, BoxLayout.Y_AXIS));
			nextCard.setBackground(ColorScheme.DARKER_GRAY_COLOR);
			nextCard.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(ColorScheme.DARKER_GRAY_HOVER_COLOR, 1),
				new EmptyBorder(8, 8, 8, 8)
			));

			JLabel nextHeader = new JLabel("\uD83D\uDD13 Next Unlock (Level " + next.getMinLevel() + ")");
			nextHeader.setFont(FontManager.getRunescapeBoldFont());
			nextHeader.setForeground(new Color(255, 185, 80));

			JLabel nextName = new JLabel(next.getName());
			nextName.setFont(FontManager.getRunescapeBoldFont());
			nextName.setForeground(Color.WHITE);

			JLabel nextRate = new JLabel("\u26A1 Expected: " + next.getRate());
			nextRate.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
			nextRate.setForeground(Color.LIGHT_GRAY);

			JLabel nextLoc = new JLabel("\uD83D\uDCCD " + next.getLocation());
			nextLoc.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
			nextLoc.setForeground(Color.GRAY);

			nextCard.add(nextHeader);
			nextCard.add(Box.createVerticalStrut(3));
			nextCard.add(nextName);
			nextCard.add(Box.createVerticalStrut(2));
			nextCard.add(nextRate);
			nextCard.add(Box.createVerticalStrut(2));
			nextCard.add(nextLoc);

			levelingContainer.add(nextCard);
		}
	}

	private void refreshTimeline(List<AstrariumEvent> events)
	{
		timelineContainer.removeAll();

		if (events.isEmpty())
		{
			JLabel empty = new JLabel("<html><center>No events recorded this session.<br>Level up or slay bosses to write your Astrarium!</center></html>", SwingConstants.CENTER);
			empty.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 11));
			empty.setForeground(Color.GRAY);
			empty.setBorder(new EmptyBorder(30, 10, 10, 10));
			timelineContainer.add(empty);
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

			timelineContainer.add(entry);
			timelineContainer.add(Box.createVerticalStrut(4));
		}
	}

	private void refreshReadiness(Client client)
	{
		readinessContainer.removeAll();

		List<ActivityReadiness.ReadinessWarning> warnings = ActivityReadiness.auditInventory(client, plugin.getCurrentSlayerTask());

		JPanel statusBanner = new JPanel(new BorderLayout());
		statusBanner.setBorder(new EmptyBorder(8, 8, 8, 8));

		if (warnings.isEmpty())
		{
			statusBanner.setBackground(new Color(25, 45, 25));
			JLabel ok = new JLabel("\u2705 All Systems Ready", SwingConstants.CENTER);
			ok.setFont(FontManager.getRunescapeBoldFont());
			ok.setForeground(new Color(100, 240, 120));
			statusBanner.add(ok, BorderLayout.CENTER);
		}
		else
		{
			statusBanner.setBackground(new Color(55, 25, 25));
			JLabel warn = new JLabel("\u26A0\uFE0F Attention Needed (" + warnings.size() + ")", SwingConstants.CENTER);
			warn.setFont(FontManager.getRunescapeBoldFont());
			warn.setForeground(new Color(255, 90, 90));
			statusBanner.add(warn, BorderLayout.CENTER);
		}

		readinessContainer.add(statusBanner);
		readinessContainer.add(Box.createVerticalStrut(8));

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

			readinessContainer.add(card);
			readinessContainer.add(Box.createVerticalStrut(4));
		}
	}
}
