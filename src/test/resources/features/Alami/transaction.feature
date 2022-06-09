@alami

Feature: Transaction to card
  @addTocard
  Scenario: add to card
    Given user navigates to "https://www.elevenia.co.id/"
    When user search "komputer" and click product best seller
    And user choose one product
    And user fill with quantity Three
    And user add to card
    And user select change kurir
    And user click on batal button
    And user click on hapus button
    And user click on ok button
    Then user see message "Tidak ada produk di Shopping Cart."
