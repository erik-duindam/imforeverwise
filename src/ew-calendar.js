'use strict';

/**
 * EWCalendar contains an O(n log n) algorithm to merge meeting time slots, given
 * an array of integers tuples in array form, i.e. [ [1,2], [3,4] ].
 *
 * The assignment said there should be no upper bound on the integers, but my assumption
 * is that the Number.MAX_SAFE_INTEGER is an acceptable upper bound. This is usually
 * 9007199254740991, which is about 6000 times the current Unix timestamp in milliseconds.
 *
 * If the assignment actually meant bigger ints than that, it would be quite easily
 * achievable by using a BigInt library instead of plain JavaScript integers.
 */
class EWCalendar {
  /**
   * Condense overlapping meeting times into a sorted list of time periods
   */
  condense_meeting_times(meetings) {
    if (!Array.isArray(meetings)) {
      throw new Error('Please provide an array of integers tuples');
    }

    // Clone array
    const merged = [...meetings];

    // Sort descending on starting time
    merged.sort((range1, range2) => range2[0] - range1[0]);

    // We'll use this to point to the (previous) time slot that we're comparing
    let pointer = 0;

    // Loop through all time slots and keep merging intervals back to the previous item
    for (let i = 0; i < merged.length; i++) {

      // Compare previous start time with current end time
      if (pointer != 0 && merged[pointer - 1][0] <= merged[i][1]) {

        // Keep overwriting the previous meeting slot as long as the next overlaps
        while (pointer != 0 && merged[pointer - 1][0] <= merged[i][1]) {

          // Overwrite the previous time slot with the new, merged interval (smallest start time)
          merged[pointer - 1][0] = Math.min(merged[pointer - 1][0], merged[i][0]);

          // Overwrite the previous time slot with the new, merged interval (largest end time)
          merged[pointer - 1][1] = Math.max(merged[pointer - 1][1], merged[i][1]);

          // Set pointer back
          pointer--;
        }
      } else {

        // Accept the current time slot as its own time slot and compare it in next iteration
        merged[pointer] = merged[i];
      }

      // Increase pointer for next iteration
      pointer++;
    }

    // The merged array still contains all the original times after the pointer index,
    // so we need to clean those up by splicing the array. Additionally, we need to reverse
    // the array. My manual for-loop is faster than doing merged.splice() + merged.reverse().
    let result = [];

    for (let i = pointer - 1; i >= 0; i--) {
      result.push(merged[i]);
    }

    return result;
  }
}

// No Babel/ES7 for simplicity
module.exports = EWCalendar;
