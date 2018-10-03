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
    appartementInput = element(by.id('field_appartement'));
    entreeInput = element(by.id('field_entree'));
    numeroInput = element(by.id('field_numero'));
    voieInput = element(by.id('field_voie'));
    complementInput = element(by.id('field_complement'));
    codepostalInput = element(by.id('field_codepostal'));
    villeInput = element(by.id('field_ville'));
    latitudeInput = element(by.id('field_latitude'));
    longitudeInput = element(by.id('field_longitude'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setAppartementInput(appartement) {
        await this.appartementInput.sendKeys(appartement);
    }

    async getAppartementInput() {
        return this.appartementInput.getAttribute('value');
    }

    async setEntreeInput(entree) {
        await this.entreeInput.sendKeys(entree);
    }

    async getEntreeInput() {
        return this.entreeInput.getAttribute('value');
    }

    async setNumeroInput(numero) {
        await this.numeroInput.sendKeys(numero);
    }

    async getNumeroInput() {
        return this.numeroInput.getAttribute('value');
    }

    async setVoieInput(voie) {
        await this.voieInput.sendKeys(voie);
    }

    async getVoieInput() {
        return this.voieInput.getAttribute('value');
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

    async setLatitudeInput(latitude) {
        await this.latitudeInput.sendKeys(latitude);
    }

    async getLatitudeInput() {
        return this.latitudeInput.getAttribute('value');
    }

    async setLongitudeInput(longitude) {
        await this.longitudeInput.sendKeys(longitude);
    }

    async getLongitudeInput() {
        return this.longitudeInput.getAttribute('value');
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
