Feature: Mobile App Controls Validation

  Scenario: Validate text box, checkbox, radio button
    Given Launch the app
    When Navigate to Views and Controls
    Then Validate that text box is visible
    And Validate that checkbox is clickable
    And Validate that radio button is clickable
    When Enter text "Appium Demo Test" in text box
    And Toggle the checkbox
    And Select the radio button
    Then Verify checkbox is selected
    And Verify radio button is selected
    And close the app and shutdown the mobile