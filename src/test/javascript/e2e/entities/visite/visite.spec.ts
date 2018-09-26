/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { VisiteComponentsPage, VisiteDeleteDialog, VisiteUpdatePage } from './visite.page-object';

const expect = chai.expect;

describe('Visite e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let visiteUpdatePage: VisiteUpdatePage;
    let visiteComponentsPage: VisiteComponentsPage;
    let visiteDeleteDialog: VisiteDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Visites', async () => {
        await navBarPage.goToEntity('visite');
        visiteComponentsPage = new VisiteComponentsPage();
        expect(await visiteComponentsPage.getTitle()).to.eq('Visites');
    });

    it('should load create Visite page', async () => {
        await visiteComponentsPage.clickOnCreateButton();
        visiteUpdatePage = new VisiteUpdatePage();
        expect(await visiteUpdatePage.getPageTitle()).to.eq('Create or edit a Visite');
        await visiteUpdatePage.cancel();
    });

    it('should create and save Visites', async () => {
        const nbButtonsBeforeCreate = await visiteComponentsPage.countDeleteButtons();

        await visiteComponentsPage.clickOnCreateButton();
        await visiteUpdatePage.setLabelInput('label');
        expect(await visiteUpdatePage.getLabelInput()).to.eq('label');
        await visiteUpdatePage.setDateInput('2000-12-31');
        expect(await visiteUpdatePage.getDateInput()).to.eq('2000-12-31');
        await visiteUpdatePage.bienSelectLastOption();
        await visiteUpdatePage.documentSelectLastOption();
        await visiteUpdatePage.save();
        expect(await visiteUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await visiteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Visite', async () => {
        const nbButtonsBeforeDelete = await visiteComponentsPage.countDeleteButtons();
        await visiteComponentsPage.clickOnLastDeleteButton();

        visiteDeleteDialog = new VisiteDeleteDialog();
        expect(await visiteDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Visite?');
        await visiteDeleteDialog.clickOnConfirmButton();

        expect(await visiteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
