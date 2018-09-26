import { element, by, ElementFinder } from 'protractor';

export class ContactComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-contact div table .btn-danger'));
    title = element.all(by.css('jhi-contact div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getText();
    }
}

export class ContactUpdatePage {
    pageTitle = element(by.id('jhi-contact-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    labelInput = element(by.id('field_label'));
    loginInput = element(by.id('field_login'));
    prenomInput = element(by.id('field_prenom'));
    nomInput = element(by.id('field_nom'));
    emailInput = element(by.id('field_email'));
    telephoneMobileInput = element(by.id('field_telephoneMobile'));
    telephone2Input = element(by.id('field_telephone2'));
    faxInput = element(by.id('field_fax'));
    descriptionInput = element(by.id('field_description'));
    posteInput = element(by.id('field_poste'));
    corpmetierSelect = element(by.id('field_corpmetier'));
    adressecontactSelect = element(by.id('field_adressecontact'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setLabelInput(label) {
        await this.labelInput.sendKeys(label);
    }

    async getLabelInput() {
        return this.labelInput.getAttribute('value');
    }

    async setLoginInput(login) {
        await this.loginInput.sendKeys(login);
    }

    async getLoginInput() {
        return this.loginInput.getAttribute('value');
    }

    async setPrenomInput(prenom) {
        await this.prenomInput.sendKeys(prenom);
    }

    async getPrenomInput() {
        return this.prenomInput.getAttribute('value');
    }

    async setNomInput(nom) {
        await this.nomInput.sendKeys(nom);
    }

    async getNomInput() {
        return this.nomInput.getAttribute('value');
    }

    async setEmailInput(email) {
        await this.emailInput.sendKeys(email);
    }

    async getEmailInput() {
        return this.emailInput.getAttribute('value');
    }

    async setTelephoneMobileInput(telephoneMobile) {
        await this.telephoneMobileInput.sendKeys(telephoneMobile);
    }

    async getTelephoneMobileInput() {
        return this.telephoneMobileInput.getAttribute('value');
    }

    async setTelephone2Input(telephone2) {
        await this.telephone2Input.sendKeys(telephone2);
    }

    async getTelephone2Input() {
        return this.telephone2Input.getAttribute('value');
    }

    async setFaxInput(fax) {
        await this.faxInput.sendKeys(fax);
    }

    async getFaxInput() {
        return this.faxInput.getAttribute('value');
    }

    async setDescriptionInput(description) {
        await this.descriptionInput.sendKeys(description);
    }

    async getDescriptionInput() {
        return this.descriptionInput.getAttribute('value');
    }

    async setPosteInput(poste) {
        await this.posteInput.sendKeys(poste);
    }

    async getPosteInput() {
        return this.posteInput.getAttribute('value');
    }

    async setCorpmetierSelect(corpmetier) {
        await this.corpmetierSelect.sendKeys(corpmetier);
    }

    async getCorpmetierSelect() {
        return this.corpmetierSelect.element(by.css('option:checked')).getText();
    }

    async corpmetierSelectLastOption() {
        await this.corpmetierSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async adressecontactSelectLastOption() {
        await this.adressecontactSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async adressecontactSelectOption(option) {
        await this.adressecontactSelect.sendKeys(option);
    }

    getAdressecontactSelect(): ElementFinder {
        return this.adressecontactSelect;
    }

    async getAdressecontactSelectedOption() {
        return this.adressecontactSelect.element(by.css('option:checked')).getText();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class ContactDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-contact-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-contact'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
