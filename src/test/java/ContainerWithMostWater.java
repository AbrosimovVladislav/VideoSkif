public class ContainerWithMostWater {

  public static void main(String[] args) {
    int[] height = new int[]{1,1};
    int expected = 49;
    int result = maxArea(height);
    System.out.println(result);
    System.out.println(expected);
    System.out.println(expected == result);
  }

  //Input: height = [1,8,6,2,5,4,8,3,7]
  public static int maxArea(int[] height) {
    int firstPos = 0; //2
    int secondPos = height.length - 1; //6
    boolean nonExitFlag = true;

    int firstElement = height[firstPos]; //6
    int secondElement = height[secondPos]; //8
    int volume = calculateVolume(firstElement, firstPos, secondElement, secondPos); //49

    while (nonExitFlag) {

      if(firstElement>secondElement){
        secondPos = secondPos-1;
        secondElement = height[secondPos];
      } else {
        firstPos = firstPos + 1;
        firstElement = height[firstPos];
      }

      int newVolume = calculateVolume(firstElement, firstPos, secondElement, secondPos);

      if(newVolume>volume){
        volume = newVolume;
      }

      if(firstPos>=secondPos){
        nonExitFlag = false;
      }

    }

    return volume;

  }

  public static int calculateVolume(int firstElem, int firstPos, int secondElem, int secondPos) {
    return Math.min(firstElem, secondElem) * (secondPos - firstPos);
  }

}

/*
11. Container With Most Water
You are given an integer array height of length n. There are n vertical lines drawn such that the
two endpoints of the ith line are (i, 0) and (i, height[i]).
Find two lines that together with the x-axis form a container, such that the container contains
the most water.
Return the maximum amount of water a container can store.
Notice that you may not slant the container.



Example 1:
Input: height = [1,8,6,2,5,4,8,3,7]
Output: 49
Explanation: The above vertical lines are represented by array [1,8,6,2,5,4,8,3,7].
In this case, the max area of water (blue section) the container can contain is 49.

Example 2:
Input: height = [1,1]
Output: 1

Constraints:
n == height.length
2 <= n <= 105
0 <= height[i] <= 104
 */
