import { element, by, ElementFinder } from 'protractor';

export class AdresseComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-adresse div table .btn-danger'));
    title = element.all(by.css('jhi-adresse div h2#page-heading span')).first();

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

export class AdresseUpdatePage {
    pageTitle = element(by.id('jhi-adresse-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    numeroInput = element(by.id('field_numero'));
    rueInput = element(by.id('field_rue'));
    complementInput = element(by.id('field_complement'));
    codepostalInput = element(by.id('field_codepostal'));
    villeInput = element(by.id('field_ville'));
    positionxInput = element(by.id('field_positionx'));
    positionyInput = element(by.id('field_positiony'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setNumeroInput(numero) {
        await this.numeroInput.sendKeys(numero);
    }

    async getNumeroInput() {
        return this.numeroInput.getAttribute('value');
    }

    async setRueInput(rue) {
        await this.rueInput.sendKeys(rue);
    }

    async getRueInput() {
        return this.rueInput.getAttribute('value');
    }

    async setComplementInput(complement) {
        await this.complementInput.sendKeys(complement);
    }

    async getComplementInput() {
        return this.complementInput.getAttribute('value');
    }

    async setCodepostalInput(codepostal) {
        await this.codepostalInput.sendKeys(codepostal);
    }

    async getCodepostalInput() {
        return this.codepostalInput.getAttribute('value');
    }

    async setVilleInput(ville) {
        await this.villeInput.sendKeys(ville);
    }

    async getVilleInput() {
        return this.villeInput.getAttribute('value');
    }

    async setPositionxInput(positionx) {
        await this.positionxInput.sendKeys(positionx);
    }

    async getPositionxInput() {
        return this.positionxInput.getAttribute('value');
    }

    async setPositionyInput(positiony) {
        await this.positionyInput.sendKeys(positiony);
    }

    async getPositionyInput() {
        return this.positionyInput.getAttribute('value');
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

export class AdresseDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-adresse-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-adresse'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
