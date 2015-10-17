package aurelienribon.slidinglayout.demo;

import aurelienribon.slidinglayout.SLAnimator;
import aurelienribon.slidinglayout.SLConfig;
import aurelienribon.slidinglayout.SLKeyframe;
import aurelienribon.slidinglayout.SLPanel;
import aurelienribon.slidinglayout.SLSide;
import controller.ServerService;
import controller.SessionListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class TheFrame extends JFrame {
	private final SLPanel panel = new SLPanel();
	public final ThePanel chatpanel = new ChatPanel("3", "data/img3.jpg");
	private final ThePanel listfiendpanel = new ListFriendPanel("1", "data/img1.jpg", (ChatPanel)chatpanel  );
	private final ThePanel searchonserver = new SearchOnServer("2", "data/img2.jpg",(ListFriendPanel)listfiendpanel, this);
	private final ThePanel signuppanel = new JSignupPanel(this, "4", "data/img4.jpg");
	private final ThePanel loginpanel = new JLoginPanel(this, "5", "data/img5.jpg");
	private final ThePanel loadfilepanel = new LoadFile("5", "data/img5.jpg");
	private final ThePanel profile = new Profile("5", "data/img3.jpg", this);
	
	private final SLConfig LoginCfg,SignupCfg,ChatCfg,DownloadCfg,SearchCfg,ChatOnlyCfg;
	public TheFrame() {
		createServerConnection();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Chat App");
		getContentPane().setBackground(Color.WHITE);
		getContentPane().add(panel, BorderLayout.CENTER);

		searchonserver.setAction(searchonserverAction);
		signuppanel.setAction(signuppanelAction);
		chatpanel.setAction(chatpanelAction);
		listfiendpanel.setAction(listfiendpanelAction);
		loginpanel.setAction(loginpanelAction);
		loadfilepanel.setAction(loadfilepanelAction);
		
		((ChatPanel)chatpanel).loadFile = (LoadFile) loadfilepanel;
		LoginCfg = new SLConfig(panel)
			.gap(10, 10)
			.row(1f).col(1f)
			.place(0, 0, loginpanel);
		
		SignupCfg = new SLConfig(panel)
			.gap(10, 10)
			.row(1f).col(1f)
			.place(0, 0, signuppanel);
		
		ChatCfg = new SLConfig(panel)
			.gap(10, 10)
			.row(1f).col(2f).col(1f)
			//.place(0, 0, searchonserver)
			.beginGrid(0, 0)
				.row(6f).row(1f).col(1f)
				.place(0, 0, chatpanel)
				.place(1, 0, loadfilepanel)
			.endGrid()
			.place(0, 1, listfiendpanel);

		SearchCfg = new SLConfig(panel)
			.gap(10, 10)
			.row(1f).col(1f).col(1f).col(1f)
			.place(0, 0, listfiendpanel)
			.place(0, 1, searchonserver)
			.place(0, 2, profile);

		DownloadCfg = new SLConfig(panel)
		.gap(10, 10)
		.row(1f).col(1f)
		.place(0, 0, loadfilepanel);

		ChatOnlyCfg = new SLConfig(panel)
		.gap(10, 10)
		.row(1f).col(1f)
		.place(0,0,chatpanel);
		
		panel.setTweenManager(SLAnimator.createTweenManager());
		panel.initialize(LoginCfg);
		//panel.initialize(ChatCfg);
		//checklogout();
	}

	/**
	 * Called when Login Success
	 * Change monitor from LoginConfigue to ChatConfigue
	 */
	public void Login2Chat(){
		panel.initialize(ChatCfg);
	}
	
	/**
	 * Called when Logout Success
	 * Change monitor from ChatConfigue to LoginConfigue
	 */
	public void Chat2Login(){
		panel.initialize(LoginCfg);
	}
	
	private void disableActions() {
		searchonserver.disableAction();
		signuppanel.disableAction();
		chatpanel.disableAction();
		listfiendpanel.disableAction();
		loginpanel.disableAction();
	}

	private void enableActions() {
		searchonserver.enableAction();
		signuppanel.enableAction();
		chatpanel.enableAction();
		listfiendpanel.enableAction();
		loginpanel.enableAction();
	}

	private final Runnable loadfilepanelAction = new Runnable() {@Override public void run() {
		panel.createTransition()
			.push(new SLKeyframe(DownloadCfg, 0.6f)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					loadfilepanel.setAction(loadfilepanelBackAction);
					loadfilepanel.enableAction();
				}}))
			.play();
	}};
	
	private final Runnable loadfilepanelBackAction = new Runnable() {@Override public void run() {
		panel.createTransition()
			.push(new SLKeyframe(ChatCfg, 0.6f)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					loadfilepanel.setAction(loadfilepanelAction);
					enableActions();
				}}))
			.play();
	}};
	
	private final Runnable searchonserverAction = new Runnable() {@Override public void run() {
		panel.createTransition()
		.push(new SLKeyframe(ChatCfg, 0.6f)
			.setEndSide(SLSide.RIGHT, searchonserver, profile)
			.setStartSide(SLSide.LEFT, chatpanel,loadfilepanel)
			.setCallback(new SLKeyframe.Callback() {@Override public void done() {
				listfiendpanel.setAction(listfiendpanelAction);
				enableActions();
			}}))
		.play();
	}};

	private final Runnable chatpanelAction = new Runnable() {@Override public void run() {
		panel.createTransition()
			.push(new SLKeyframe(ChatOnlyCfg, 0.6f)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					chatpanel.setAction(chatpanelBackAction);
					chatpanel.enableAction();
				}}))
			.play();
	}};
	private final Runnable chatpanelBackAction = new Runnable() {@Override public void run() {
		disableActions();

		panel.createTransition()
			.push(new SLKeyframe(ChatCfg, 0.6f)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					chatpanel.setAction(chatpanelAction);
					enableActions();
				}}))
			.play();
	}};

	private final Runnable listfiendpanelAction = new Runnable() {@Override public void run() {
		//disableActions();

		panel.createTransition()
			.push(new SLKeyframe(SearchCfg, 0.6f)
				.setEndSide(SLSide.LEFT, chatpanel,loadfilepanel)
				.setStartSide(SLSide.RIGHT, searchonserver, profile)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					listfiendpanel.setAction(listfiendpanelBackAction);
					listfiendpanel.enableAction();
				}}))
			.play();
	}};

	private final Runnable listfiendpanelBackAction = new Runnable() {@Override public void run() {
		disableActions();

		panel.createTransition()
			.push(new SLKeyframe(ChatCfg, 0.6f)
				.setEndSide(SLSide.RIGHT, searchonserver, profile)
				.setStartSide(SLSide.LEFT, chatpanel,loadfilepanel)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					listfiendpanel.setAction(listfiendpanelAction);
					enableActions();
				}}))
			.play();
	}};

	private final Runnable loginpanelAction = new Runnable() {@Override public void run() {

		panel.createTransition()
			.push(new SLKeyframe(SignupCfg, 0.8f)
				.setEndSide(SLSide.LEFT, loginpanel)
				.setStartSide(SLSide.RIGHT , signuppanel)
				)
			.play();
	}};
	
	private final Runnable signuppanelAction = new Runnable() {@Override public void run() {

		panel.createTransition()
			.push(new SLKeyframe(LoginCfg, 0.6f)
				.setEndSide(SLSide.RIGHT , signuppanel)
				.setStartSide(SLSide.LEFT, loginpanel)
					)
			.play();
	}};

	public void createServerConnection(){
		try {
			ServerService.GetResource();
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "Error: Can'nt to server");
			System.exit(0);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error: Can'nt to server");
			System.exit(0);
		}
		// Setup session
		try {
			ServerService.GetResource().session.addSessionListener((SessionListener)listfiendpanel);
			ServerService.GetResource().session.addSessionListener((SessionListener)profile);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
