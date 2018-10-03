/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AdresseComponentsPage, AdresseDeleteDialog, AdresseUpdatePage } from './adresse.page-object';

const expect = chai.expect;

describe('Adresse e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let adresseUpdatePage: AdresseUpdatePage;
    let adresseComponentsPage: AdresseComponentsPage;
    let adresseDeleteDialog: AdresseDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Adresses', async () => {
        await navBarPage.goToEntity('adresse');
        adresseComponentsPage = new AdresseComponentsPage();
        expect(await adresseComponentsPage.getTitle()).to.eq('Adresses');
    });

    it('should load create Adresse page', async () => {
        await adresseComponentsPage.clickOnCreateButton();
        adresseUpdatePage = new AdresseUpdatePage();
        expect(await adresseUpdatePage.getPageTitle()).to.eq('Create or edit a Adresse');
        await adresseUpdatePage.cancel();
    });

    it('should create and save Adresses', async () => {
        const nbButtonsBeforeCreate = await adresseComponentsPage.countDeleteButtons();

        await adresseComponentsPage.clickOnCreateButton();
        await adresseUpdatePage.setAppartementInput('appartement');
        expect(await adresseUpdatePage.getAppartementInput()).to.eq('appartement');
        await adresseUpdatePage.setEntreeInput('entree');
        expect(await adresseUpdatePage.getEntreeInput()).to.eq('entree');
        await adresseUpdatePage.setNumeroInput('numero');
        expect(await adresseUpdatePage.getNumeroInput()).to.eq('numero');
        await adresseUpdatePage.setVoieInput('voie');
        expect(await adresseUpdatePage.getVoieInput()).to.eq('voie');
        await adresseUpdatePage.setComplementInput('complement');
        expect(await adresseUpdatePage.getComplementInput()).to.eq('complement');
        await adresseUpdatePage.setCodepostalInput('5');
        expect(await adresseUpdatePage.getCodepostalInput()).to.eq('5');
        await adresseUpdatePage.setVilleInput('ville');
        expect(await adresseUpdatePage.getVilleInput()).to.eq('ville');
        await adresseUpdatePage.setLatitudeInput('latitude');
        expect(await adresseUpdatePage.getLatitudeInput()).to.eq('latitude');
        await adresseUpdatePage.setLongitudeInput('longitude');
        expect(await adresseUpdatePage.getLongitudeInput()).to.eq('longitude');
        await adresseUpdatePage.save();
        expect(await adresseUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await adresseComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Adresse', async () => {
        const nbButtonsBeforeDelete = await adresseComponentsPage.countDeleteButtons();
        await adresseComponentsPage.clickOnLastDeleteButton();

        adresseDeleteDialog = new AdresseDeleteDialog();
        expect(await adresseDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Adresse?');
        await adresseDeleteDialog.clickOnConfirmButton();

        expect(await adresseComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
