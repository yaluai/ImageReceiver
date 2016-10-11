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
/**
 * A Single-linked linkedlist with a "dumb" header node (no data in the node), but
 * without a tail node. It implements ListADT<E> and returns
 * PacketLinkedListIterator when requiring a iterator.
 */
public class PacketLinkedList<E> implements ListADT<E> {
	// TODO: Add your fields here. 
	//       Please see ListADT for detailed javadoc
	//
	private Listnode<E> head;
	private int _size;

	/**
	 * Constructs a empty PacketLinkedList
	 */
	public PacketLinkedList() {
		// TODO
		this.head = new Listnode<E>(null);
		this._size = 0;
	}

	@Override
	public void add(E item) {
		// TODO
		if (null==item) {
			throw new IllegalArgumentException("This item is null, cannot be added to the list.");
		}
		Listnode<E> pNode = this.head;
		while (pNode.getNext() != null){
			pNode = pNode.getNext();
		}
		pNode.setNext(new Listnode<E>(item));
		this._size ++;
	}

	@Override
	public void add(int pos, E item) {
		// TODO
		if (null==item) {
			throw new IllegalArgumentException("This item is null, cannot be added to the list.");
		}
		if (pos<0 || pos>this._size) {
			throw new IndexOutOfBoundsException("This position is out of bound.");
		}
		Listnode<E> pNode = this.head;
		for (int i=0; i<pos; i++) {
			pNode = pNode.getNext();
		}
		Listnode<E> nextNode = pNode.getNext(); 
		pNode.setNext(new Listnode<E>(item));
		pNode.getNext().setNext(nextNode);
		this._size ++;
	}

	@Override
	public boolean contains(E item) {
		// TODO: replace the default return statement
		boolean isItemContained = false;
		Listnode<E> pNode = this.head;
		while(pNode.getNext()!=null) {
			pNode = pNode.getNext();
			if(pNode.getData().equals(item)) {
				isItemContained = true;
			}
		}
		return isItemContained;
	}

	@Override
	public E get(int pos) {
		// TODO: replace the default return statement
		if (pos<0 || pos>=this._size) {
			throw new IndexOutOfBoundsException("This position is out of bound.");
		}
		Listnode<E> pNode = this.head;
		for (int i=0; i<pos; i++) {
			pNode = pNode.getNext();
		}
		return pNode.getNext().getData();
	}

	@Override
	public boolean isEmpty() {
		// TODO: replace the default return statement
		if (this._size==0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public E remove(int pos) {
		// TODO: replace the default return statement
		if (pos<0 || pos>=this._size) {
			throw new IndexOutOfBoundsException("This position is out of bound.");
		}
		Listnode<E> pNode = this.head;
		for (int i=0; i<pos; i++) {
			pNode = pNode.getNext();
		}
		E removedItem = pNode.getNext().getData(); 
		pNode.setNext(pNode.getNext().getNext());
		this._size --;
		return removedItem;
	}

	@Override
	public int size() {
		// TODO: replace the default return statement
		return this._size;
	}

	@Override
	public PacketLinkedListIterator<E> iterator() {
		// TODO: replace the default return statement
		PacketLinkedListIterator<E> listIterator = new PacketLinkedListIterator<E>(this.head);
		return listIterator;
	}

}
