public class Palindrome{
	public Deque<Character> wordToDeque(String word){
		Deque<Character> d = new LinkedListDeque<Character> ();
		for (int i = 0; i < word.length(); i++){
			char aChar = word.charAt(i);
			d.addLast(aChar);
		}
		return d;
	}

	private boolean isPalindromeHelper(Deque d) {
		if (d.size() <= 1) {
			return true;
		} else if (d.removeFirst() != d.removeLast()) {
			return false;
		} else {
			return isPalindromeHelper(d);
		}
	}

	public boolean isPalindrome(String word) {
		Deque d = wordToDeque(word);
		return isPalindromeHelper(d);
	}

	// overloads
	public boolean isPalindrome(String word, CharacterComparator cc) {
		Deque d = wordToDeque(word);
		return isPalindromeHelper(d, cc);
	}
	// overloads
	private boolean isPalindromeHelper(Deque d, CharacterComparator cc) {
		if (d.size() <= 1) {
			return true;
		} else if (!cc.equalChars((char)d.removeFirst(), (char)d.removeLast())) {
			return false;
		} else {
			return isPalindromeHelper(d, cc);
		}
	}
}