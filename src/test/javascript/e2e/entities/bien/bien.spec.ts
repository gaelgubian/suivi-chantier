/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { BienComponentsPage, BienDeleteDialog, BienUpdatePage } from './bien.page-object';

const expect = chai.expect;

describe('Bien e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let bienUpdatePage: BienUpdatePage;
    let bienComponentsPage: BienComponentsPage;
    let bienDeleteDialog: BienDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Biens', async () => {
        await navBarPage.goToEntity('bien');
        bienComponentsPage = new BienComponentsPage();
        expect(await bienComponentsPage.getTitle()).to.eq('Biens');
    });

    it('should load create Bien page', async () => {
        await bienComponentsPage.clickOnCreateButton();
        bienUpdatePage = new BienUpdatePage();
        expect(await bienUpdatePage.getPageTitle()).to.eq('Create or edit a Bien');
        await bienUpdatePage.cancel();
    });

    it('should create and save Biens', async () => {
        const nbButtonsBeforeCreate = await bienComponentsPage.countDeleteButtons();

        await bienComponentsPage.clickOnCreateButton();
        await bienUpdatePage.setLabelInput('label');
        expect(await bienUpdatePage.getLabelInput()).to.eq('label');
        await bienUpdatePage.setDescriptionInput('description');
        expect(await bienUpdatePage.getDescriptionInput()).to.eq('description');
        await bienUpdatePage.chantierSelectLastOption();
        await bienUpdatePage.adressechantierSelectLastOption();
        await bienUpdatePage.save();
        expect(await bienUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await bienComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Bien', async () => {
        const nbButtonsBeforeDelete = await bienComponentsPage.countDeleteButtons();
        await bienComponentsPage.clickOnLastDeleteButton();

        bienDeleteDialog = new BienDeleteDialog();
        expect(await bienDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Bien?');
        await bienDeleteDialog.clickOnConfirmButton();

        expect(await bienComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
