package com.kukzze.chronicle;

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
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;

public class ChroniclePanel extends PluginPanel
{
	private static final String TAB_ROADMAP = "ROADMAP";
	private static final String TAB_TIMELINE = "TIMELINE";
	private static final String TAB_READINESS = "READINESS";

	private final ChroniclePlugin plugin;
	private final ChronicleConfig config;

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
	private final JPanel timelineContainer = new JPanel();
	private final JPanel readinessContainer = new JPanel();

	private final JButton roadmapNavBtn = new JButton("\uD83E\uDDED Roadmap");
	private final JButton timelineNavBtn = new JButton("\uD83D\uDCDC Timeline");
	private final JButton readinessNavBtn = new JButton("\uD83C\uDF92 Ready");

	public ChroniclePanel(ChroniclePlugin plugin, ChronicleConfig config)
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
				ChronicleTheme theme = config.theme();
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
		headerCard.setBorder(new EmptyBorder(12, 12, 12, 12));
		headerCard.setOpaque(false);

		nameLabel = new JLabel("Chronicle Adventurer", SwingConstants.CENTER);
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
		headerCard.add(Box.createVerticalStrut(4));
		headerCard.add(combatLevelLabel);
		headerCard.add(Box.createVerticalStrut(6));
		headerCard.add(combatProgressBar);
		headerCard.add(Box.createVerticalStrut(4));
		headerCard.add(nextCombatMathLabel);
		headerCard.add(Box.createVerticalStrut(4));
		headerCard.add(totalLevelLabel);

		// Navigation Bar
		JPanel navBar = new JPanel(new GridLayout(1, 3, 4, 0));
		navBar.setBorder(new EmptyBorder(8, 6, 8, 6));
		navBar.setBackground(ColorScheme.DARKER_GRAY_COLOR);

		styleNavButton(roadmapNavBtn, true);
		styleNavButton(timelineNavBtn, false);
		styleNavButton(readinessNavBtn, false);

		roadmapNavBtn.addActionListener(e -> switchTab(TAB_ROADMAP, roadmapNavBtn));
		timelineNavBtn.addActionListener(e -> switchTab(TAB_TIMELINE, timelineNavBtn));
		readinessNavBtn.addActionListener(e -> switchTab(TAB_READINESS, readinessNavBtn));

		navBar.add(roadmapNavBtn);
		navBar.add(timelineNavBtn);
		navBar.add(readinessNavBtn);

		// Container for Top elements
		JPanel topContainer = new JPanel();
		topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
		topContainer.setBackground(ColorScheme.DARK_GRAY_COLOR);
		topContainer.setBorder(new EmptyBorder(6, 6, 0, 6));
		topContainer.add(headerCard);
		topContainer.add(navBar);

		add(topContainer, BorderLayout.NORTH);

		// Content Panes
		setupContentPanel(roadmapContainer);
		setupContentPanel(timelineContainer);
		setupContentPanel(readinessContainer);

		contentCards.add(createScrollPane(roadmapContainer), TAB_ROADMAP);
		contentCards.add(createScrollPane(timelineContainer), TAB_TIMELINE);
		contentCards.add(createScrollPane(readinessContainer), TAB_READINESS);

		add(contentCards, BorderLayout.CENTER);
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
		styleNavButton(timelineNavBtn, activeBtn == timelineNavBtn);
		styleNavButton(readinessNavBtn, activeBtn == readinessNavBtn);
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
			combatProgressBar.setString(progressPct + "% to Combat " + (combat.getCombatLevel() + 1));
			combatProgressBar.setForeground(config.theme().getProgressBarColor());

			if (config.showCombatMath())
			{
				String mathText = String.format("Next: +%d Str OR +%d Att OR +%d Def OR +%d Pray",
					combat.getStrengthNeeded(), combat.getAttackNeeded(), combat.getDefenceNeeded(), combat.getPrayerNeeded());
				nextCombatMathLabel.setText(mathText);
			}

			totalLevelLabel.setText("Total Level: " + client.getTotalLevel());

			refreshRoadmap(skills);
			refreshTimeline(plugin.getTimelineEvents());
			refreshReadiness(client);

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

	private void refreshTimeline(List<ChronicleEvent> events)
	{
		timelineContainer.removeAll();

		if (events.isEmpty())
		{
			JLabel empty = new JLabel("<html><center>No events recorded this session.<br>Level up or slay bosses to write your Chronicle!</center></html>", SwingConstants.CENTER);
			empty.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 11));
			empty.setForeground(Color.GRAY);
			empty.setBorder(new EmptyBorder(30, 10, 10, 10));
			timelineContainer.add(empty);
			return;
		}

		for (ChronicleEvent ev : events)
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
