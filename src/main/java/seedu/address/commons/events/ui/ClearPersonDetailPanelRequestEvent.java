package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author jo-lyn
/**
 * Indicates a request to clear the person detail panel.
 */
public class ClearPersonDetailPanelRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
