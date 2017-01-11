'use strict';
const expect = require('expect');
const EWCalendar = require('../src/ew-calendar');

describe('EWCalendar', () => {
  describe('#condense_meeting_times()', () => {

    it('should condense simple overlapping meetings', () => {
      const calendar = new EWCalendar();
      const times = [ [1, 2], [3, 4], [2, 5] ];
      const condensed = [ [1, 5] ];

      expect(calendar.condense_meeting_times(times)).toEqual(condensed);
    });

    it('should condense slightly more complex overlapping meetings', () => {
      const calendar = new EWCalendar();
      const times = [ [10, 12], [9, 13], [14, 1337], [2, 3], [1, 2], [0, 1]];
      const condensed = [ [0, 3], [9, 13], [14, 1337] ];

      expect(calendar.condense_meeting_times(times)).toEqual(condensed);
    });

    it('should condense meetings set in Unix timestamps', () => {
      const calendar = new EWCalendar();

      const times = [
        [1484093658, 1484093800],
        [1484093638, 1484093660],
        [1284093658, 1482093658],
        [1484094658, 1484095658],
      ];

      const condensed = [
        [1284093658, 1482093658],
        [1484093638, 1484093800],
        [1484094658, 1484095658],
      ];

      expect(calendar.condense_meeting_times(times)).toEqual(condensed);
    });

    it('should condense meetings set in Unix timestamps with milliseconds', () => {
      const calendar = new EWCalendar();

      const times = [
        [1484093658000, 1484093800000],
        [1484093638000, 1484093660000],
        [1284093658000, 1982093658000],
        [1484094658000, 1484095658000],
      ];

      const condensed = [
        [1284093658000, 1982093658000]
      ];

      expect(calendar.condense_meeting_times(times)).toEqual(condensed);
    });
  });
});
