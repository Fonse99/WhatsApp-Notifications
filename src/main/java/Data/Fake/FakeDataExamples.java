package Data.Fake;

import java.util.GregorianCalendar;

import Data.Domain.Credits;

public class FakeDataExamples {
        Credits[] fakeCreditsList = {
                        new Credits(
                                        1,
                                        "Brandon",
                                        "Fonseca",
                                        2000.00,
                                        new GregorianCalendar(2022, 06, 07).getTime(),
                                        "+50585038365",
                                        "isaac99.bf@gmail.com",
                                        'M'),
        };

        public Credits[] getAllCredits() {
                return this.fakeCreditsList;
        }

}