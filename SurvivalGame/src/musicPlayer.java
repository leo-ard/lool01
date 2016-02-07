import java.io.FileInputStream;
import java.io.FileNotFoundException;

class musicPlayer implements Runnable {
	private String[] filePath;
	private javazoom.jl.player.advanced.AdvancedPlayer player;
	private Thread playerThread;
	private boolean isRunning;

	public musicPlayer(String[] filePath, boolean loop) {
		this.filePath = filePath;
		isRunning = false;
	}
	
	public void play(){
		isRunning = true;
		try {

			this.player = new javazoom.jl.player.advanced.AdvancedPlayer(new FileInputStream(this.filePath[(int)((double)Math.random()*(double)(filePath.length))]),
					javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice());

			this.playerThread = new Thread(this, "AudioPlayerThread");

			this.playerThread.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
	public void stop(){
		playerThread.stop();
		isRunning = false;
	}

	// Runnable members

	public void run() {
		try {
			while(true){
				this.player.play();
				this.player = new javazoom.jl.player.advanced.AdvancedPlayer(new FileInputStream(this.filePath[(int)((double)Math.random()*(double)(filePath.length))]),
						javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice());
			}
		} catch (javazoom.jl.decoder.JavaLayerException ex) {
			ex.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	
	public boolean isRunning(){return isRunning;}
}