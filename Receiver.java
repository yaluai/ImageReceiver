///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            RUI YIN
// Files:            Receiver.java"
//"PacketLinkedList.java"
//"PacketLinkedListIterator.java"
//"BadImageHeaderException.java"
//"BadImageContentException.java"
// Semester:         (course) Spring 2016
//
// Author:           RUI YIN
// Email:            RYIN5@WISC.EDU
// CS Login:         RYIN
// Lecturer's Name:  DEB DEPPLER
// Lab Section:      2
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
//                   CHECK ASSIGNMENT PAGE TO see IF PAIR-PROGRAMMING IS ALLOWED
//                   If pair programming is allowed:
//                   1. Read PAIR-PROGRAMMING policy (in cs302 policy) 
//                   2. choose a partner wisely
//                   3. REGISTER THE TEAM BEFORE YOU WORK TOGETHER 
//                      a. one partner creates the team
//                      b. the other partner must join the team
//                   4. complete this section for each program file.
//
// Pair Partner:     (name of your pair programming partner)
// Email:            (email address of your programming partner)
// CS Login:         (partner's login name)
// Lecturer's Name:  (name of your partner's lecturer)
// Lab Section:      (your partner's lab section number)
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                   must fully acknowledge and credit those sources of help.
//                   Instructors and TAs do not have to be credited here,
//                   but tutors, roommates, relatives, strangers, etc do.
//
// Persons:          Identify persons by name, relationship to you, and email.
//                   Describe in detail the the ideas and help they provided.
//
// Online sources:   avoid web searches to solve your problems, but if you do
//                   search, be sure to include Web URLs and description of 
//                   of any information you find.
//////////////////////////// 80 columns wide //////////////////////////////////
import java.io.IOException;

/**
 * The main class. It simulates a application (image viewer) receiver by
 * maintaining a list buffer. It collects packets from the queue of 
 * InputDriver and arrange them properly, and then reconstructs the image
 * file from its list buffer.
 */
public class Receiver {
	private InputDriver input;
	private ImageDriver img;
	private PacketLinkedList<SimplePacket> list;

	/**
	 * Constructs a Receiver to obtain the image file transmitted.
	 * @param file the filename you want to receive
	 */
	public Receiver(String file) {
		try {
			input = new InputDriver(file, true);
		} catch (IOException e) {
			System.out.println("The file, " + file + ", isn't existed on the server.");
			System.exit(0);
		}
		img = new ImageDriver(input);
		// TODO: properly initialize your field
		list = new PacketLinkedList();
	}

	/**
	 * Returns the PacketLinkedList buffer in the receiver
	 * 
	 * @return the PacketLinkedList object
	 */
	public PacketLinkedList<SimplePacket> getListBuffer() {
		return list;
	}
	
	/**
	 * Asks for retransmitting the packet. The new packet with the sequence 
	 * number will arrive later by using {@link #askForNextPacket()}.
	 * Notice that ONLY packet with invalid checksum will be retransmitted.
	 * 
	 * @param pkt the packet with bad checksum
	 * @return true if the requested packet is added in the receiving queue; otherwise, false
	 */
	public boolean askForRetransmit(SimplePacket pkt) {
		return input.resendPacket(pkt);
	}

        
	/**
	 * Asks for retransmitting the packet with a sequence number. 
	 * The requested packet will arrive later by using 
	 * {@link #askForNextPacket()}. Notice that ONLY missing
	 * packet will be retransmitted. Pass seq=0 if the missing packet is the
	 * "End of Streaming Notification" packet.
	 * 
	 * @param seq the sequence number of the requested missing packet
	 * @return true if the requested packet is added in the receiving queue; otherwise, false
	 */
	public boolean askForMissingPacket(int seq) {
		return input.resendMissingPacket(seq);
	}

	/**
	 * Returns the next packet.
	 * 
	 * @return the next SimplePacket object; returns null if no more packet to
	 *         receive
	 */
	public SimplePacket askForNextPacket() {
		return input.getNextPacket();
	}

	/**
	 * Returns true if the maintained list buffer has a valid image content. Notice
	 * that when it returns false, the image buffer could either has a bad
	 * header, or just bad body, or both.
	 * 
	 * @return true if the maintained list buffer has a valid image content;
	 *         otherwise, false
	 */
	public boolean validImageContent() {
		return input.validFile(list);

	}

	/**
	 * Returns if the maintained list buffer has a valid image header
	 * 
	 * @return true if the maintained list buffer has a valid image header;
	 *         otherwise, false
	 */
	public boolean validImageHeader() {
		return input.validHeader(list.get(0));
	}
	
	/**
	 * Outputs the formatted content in the PacketLinkedList buffer. See
	 * course webpage for the formatting detail.
	 */
	public void displayList() {
		// TODO: implement this method firstly to help you debug
		String bufferString = "";
		PacketLinkedListIterator<SimplePacket> it = this.list.iterator();
		SimplePacket packet;
		while (it.hasNext()) {
			packet = it.next();
			if(packet.isValidCheckSum()) {
				bufferString = bufferString + String.valueOf(packet.getSeq()) + ",";
			} else {
				bufferString = bufferString + "[" + String.valueOf(packet.getSeq()) + "],";
			}
		}
		bufferString = bufferString.substring(0, bufferString.length()-1); 
		System.out.println(bufferString);
	}

	/**
	 * Reconstructs the file by arranging the {@link PacketLinkedList} in
	 * correct order. It uses {@link #askForNextPacket()} to get packets until
	 * no more packet to receive. It eliminates the duplicate packets and asks
	 * for retransmitting when getting a packet with invalid checksum.
	 */
	public void reconstructFile() {
		// TODO: Collect the packets and arrange them in order.
		// 		 You can try to collect all packets and put them into your list
		//       without any processing and use implemented displayList() to see
		//       the pattern of packets you are going to receive.
		//       Then, properly handle the invalid checksum and duplicates. The 
		//       first image file, "secret0.jpg", would not result in missing
		//       packets into your receiving queue such that you can test it once
		//       you get the first two processing done.
		SimplePacket packet = askForNextPacket();
		int seq = 0;
		while (true) {
			if(packet==null) {
				while (!askForMissingPacket(0)) {}
			} else {
				seq = packet.getSeq();
				if(seq > 0) {
					if(packet.isValidCheckSum()) {
						if(this.list.isEmpty()) {
							this.list.add(packet);
						} else {
							int i;
							boolean isBreakorNot = false;
							PacketLinkedListIterator<SimplePacket> it = this.list.iterator();
							for (i=0; i<this.list.size(); i++) {
								if (it.next().getSeq()>=packet.getSeq()) {
									isBreakorNot = true;
									break;
								} 
							}
							if(isBreakorNot) {
								if (this.list.get(i).getSeq()==packet.getSeq()) {
									this.list.remove(i);
									this.list.add(i, packet);
								} else {
									this.list.add(i, packet);
								}
							} else {
								this.list.add(packet);
							}
						}
					} else {
						while (!askForRetransmit(packet)) {}
					}
				} else {
					if (-seq != this.list.size()) {
						boolean[] boolList = new boolean[-seq+1]; 
						for (int i=0; i<= -seq; i++) {
							boolList[i] = false;
						} 
						PacketLinkedListIterator<SimplePacket> it = this.list.iterator();
						SimplePacket tmpPacket;
						while (it.hasNext()) {
							tmpPacket = it.next();
							boolList[tmpPacket.getSeq()] = true;
						}
						for (int i=1; i<= -seq; i++) {
							if(!boolList[i]) {
								while(!askForMissingPacket(i)) {};
							}
						} 
					}
				}
			}
			packet = askForNextPacket();
			if(-seq == this.list.size()) {
				System.out.println(""+seq);
				break;
			}
		}
		// TODO: Processing missing packets for the other four images. You should
		//       utilize the information provided by "End of Streaming Notification
		//       Packet" though this special packet could be lost while transmitting.
		//
	}

	/**
	 * Opens the image file by merging the content in the maintained list
	 * buffer.
	 */
	public void openImage() throws BadImageHeaderException, BadImageContentException{	
		try {
			img.openImage(list);
		} 
		// TODO: catch the image-related exception
		/* throws BadImageHeaderException if the maintained list buffer has an 
		 * invalid image header, throws BadImageContentException if the 
		 * maintained list buffer has an invalid image content*/
		catch (Exception e) {
			System.out.println("Please catch the proper Image-related Exception.");
			e.printStackTrace();
			if(validImageHeader()) {
				throw new BadImageHeaderException();
			}
			if(validImageContent()) {
				throw new BadImageContentException();
			}
		}
	}

	/**
	 * Initiates a Receiver to reconstruct collected packets and open the Image
	 * file, which is specified by args[0]. 
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
        if (args.length != 1) {
              System.out.println("Usage: java Receiver [filename_on_server]");
              return;
        }
		Receiver recv = new Receiver(args[0]);
		recv.reconstructFile();
        recv.displayList(); //use for debugging
		recv.openImage();
	}
}
