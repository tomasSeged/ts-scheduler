/**
 * This class represents the ScheduleSorter class.
 * @param <T> parameter type
 */
public class ScheduleSorter<T extends Comparable<T>> {

	/**
	 * Default initial capacity.
	 */
	private static final int DEFAULT_CAPACITY = 2;

	/**
	 * The array to be used.
	 */
	private T[] data;

	/**
	 * Size of the array.
	 */
	private int size;

	/**
	 * Capacity of the array.
	 */
	private int capacity = DEFAULT_CAPACITY;

	/**
	 * Constructor for this class. Sets capacity to default, which is 2.
	 */
	@SuppressWarnings("unchecked")
	public ScheduleSorter( ) {
		data = (T[]) new Comparable[DEFAULT_CAPACITY];
		capacity = DEFAULT_CAPACITY;
	}

	/**
	 * Constructor for this class. Sets capacity to provided capacity.
	 *
	 * @param initialCapacity capacity
	 */
	@SuppressWarnings("unchecked")
	public ScheduleSorter(int initialCapacity) {
		data = (T[]) new Comparable[initialCapacity];
		capacity = initialCapacity;

		if (initialCapacity < DEFAULT_CAPACITY)
			throw new IllegalArgumentException("Capacity must be at least 2!");
	}


	/**
	 * Returns size of the array.
	 *
	 * @return size of array
	 */
	public int size( ) {
		return size;
	}

	/**
	 * Returns capacity of the array.
	 *
	 * @return capacity
	 */
	public int capacity( ) {
		return capacity;
	}

	/**
	 * Private method that sorts the array at O(1) time complexity.
	 */
	private void sort_dynamic_array( ) {
		for (int i = 0; i < size - 1; i++) {
			if (data[i].compareTo(data[i + 1]) > 0) {
				T temp = data[i];
				data[i] = data[i + 1];
				data[i + 1] = temp;
				i = -1;
			}
		}
	}

	/**
	 * Insert the given value into the array and keep the array sorted in ascending order.
	 *
	 * @param value value to be added into array
	 */
	public void add(T value) {

		if (value == null) {
			throw new IllegalArgumentException("Cannot add: null value!");
		}

		if (capacity == Integer.MAX_VALUE - 50)
			throw new IllegalStateException("Cannot add: capacity upper-bound reached!");

		if (size == capacity) {
			doubleCapacity();
		}

		data[size] = value;
		size++;
		sort_dynamic_array();
	}

	/**
	 * Return the item at the given index.
	 *
	 * @param index index of item
	 * @return value at given index
	 */
	public T get(int index) {

		if (index >= size || index < 0) {
			throw new IndexOutOfBoundsException("Index " + index + " out of bounds!");
		}

		return data[index];
	}

	/**
	 * Change the item at the given index to be the given value.
	 *
	 * @param index index
	 * @param value new value
	 * @return true if successful, otherwise false.
	 */
	public boolean replace(int index, T value) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException("Index " + index + " out of bounds!");
		}

		if (value == null) {
			throw new IllegalArgumentException("Cannot add: null value!");
		}

		if ((value.compareTo(data[index - 1]) > 0 || value.compareTo(data[index - 1]) == 0) && (value.compareTo(data[index + 1]) < 0 || value.compareTo(data[index + 1]) == 0)) {
			data[index] = value;
			return true;
		}

		return false;
	}

	/**
	 * Insert the given value at the given index. Shift elements if needed.
	 *
	 * @param index index
	 * @param value new value to be inserted
	 * @return true if successful, otherwise false.
	 */
	public boolean add(int index, T value) {

		if (index > capacity() && index < 0) {
			throw new IndexOutOfBoundsException("Index " + index + " out of bounds!");
		} else if (value == null) {
			throw new IllegalArgumentException("Cannot add: null value!");
		}

		if (capacity == Integer.MAX_VALUE - 50)
			throw new IllegalStateException("Cannot add: capacity upper-bound reached!");

		if (size == capacity) {
			doubleCapacity();
		}

		if (size == 0) {
			data[0] = value;
			size++;
			return true;
		}

		if (data[index] == null && data[index - 1] != null && index == size) {
			if (value.compareTo(data[index - 1]) > 0) {
				data[index] = value;
				size++;
				return true;
			} else
				return false;
		} else if (data[index] == null && data[index - 1] == null) {
			return false;
		}

		if ((data[index - 1].compareTo(value) < 0 || data[index - 1].compareTo(value) == 0) && (data[index].compareTo(value) > 0 || data[index].compareTo(value) == 0)) {
			for (int i = size; i > index; i--) {
				data[i] = data[i - 1];
			}
			data[index] = value;
			size++;
			return true;
		}

		return true;
	}


	/**
	 * Remove and return the element at the given index.
	 *
	 * @param index index
	 * @return element at given index.
	 */
	public T delete(int index) {
		if (index >= size() && index < 0) {
			throw new IndexOutOfBoundsException("Index " + index + " out of bounds!");
		}
		T temp = data[index];

		for (int i = index; i < size() - 1; i++) {
			data[i] = data[i + 1];
		}

		data[size() - 1] = null;
		size--;

		if (size * 3 < capacity) {
			halveCapacity();
		}

		return temp;
	}

	/**
	 * Double the max number of items allowed in data storage.
	 *
	 * @return true if successful, otherwise false.
	 */
	@SuppressWarnings("unchecked")
	public boolean doubleCapacity( ) {
		int upperBound = Integer.MAX_VALUE - 50;
		int newCap = data.length + data.length;

		if (newCap > upperBound) {
			newCap = upperBound;
		}

		if (capacity == upperBound) {
			return false;
		}

		if (newCap < DEFAULT_CAPACITY) {
			newCap = DEFAULT_CAPACITY;
		}

		T[] newData = (T[]) new Comparable[newCap];

		for (int i = 0; i < size; i++) {
			newData[i] = data[i];
		}

		capacity = newCap;
		data = newData;

		return true;
	}

	/**
	 * Reduce the max number of items allowed in data storage by half.
	 *
	 * @return true if successful, otherwise false.
	 */
	@SuppressWarnings("unchecked")
	public boolean halveCapacity( ) {
		T bufferArray[] = null;

		if (capacity % 2 != 0 && capacity < size) {
			Math.floor(capacity);
		}

		if (capacity == size) {
			return false;
		}

		if ((capacity / 2) < 2) {
			if (size > DEFAULT_CAPACITY)
				return false;

			bufferArray = (T[]) new Comparable[DEFAULT_CAPACITY];
			return true;
		}

		bufferArray = (T[]) new Comparable[capacity / 2];

		for (int i = 0; i < size; i++) {
			bufferArray[i] = data[i];
		}
		capacity = capacity / 2;
		data = bufferArray;

		return true;
	}

}