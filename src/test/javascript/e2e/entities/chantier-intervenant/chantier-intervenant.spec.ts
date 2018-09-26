/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    ChantierIntervenantComponentsPage,
    ChantierIntervenantDeleteDialog,
    ChantierIntervenantUpdatePage
} from './chantier-intervenant.page-object';

const expect = chai.expect;

describe('ChantierIntervenant e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let chantierIntervenantUpdatePage: ChantierIntervenantUpdatePage;
    let chantierIntervenantComponentsPage: ChantierIntervenantComponentsPage;
    let chantierIntervenantDeleteDialog: ChantierIntervenantDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ChantierIntervenants', async () => {
        await navBarPage.goToEntity('chantier-intervenant');
        chantierIntervenantComponentsPage = new ChantierIntervenantComponentsPage();
        expect(await chantierIntervenantComponentsPage.getTitle()).to.eq('Chantier Intervenants');
    });

    it('should load create ChantierIntervenant page', async () => {
        await chantierIntervenantComponentsPage.clickOnCreateButton();
        chantierIntervenantUpdatePage = new ChantierIntervenantUpdatePage();
        expect(await chantierIntervenantUpdatePage.getPageTitle()).to.eq('Create or edit a Chantier Intervenant');
        await chantierIntervenantUpdatePage.cancel();
    });

    it('should create and save ChantierIntervenants', async () => {
        const nbButtonsBeforeCreate = await chantierIntervenantComponentsPage.countDeleteButtons();

        await chantierIntervenantComponentsPage.clickOnCreateButton();
        await chantierIntervenantUpdatePage.corpmetierSelectLastOption();
        await chantierIntervenantUpdatePage.roleSelectLastOption();
        await chantierIntervenantUpdatePage.contactSelectLastOption();
        await chantierIntervenantUpdatePage.chantierSelectLastOption();
        await chantierIntervenantUpdatePage.save();
        expect(await chantierIntervenantUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await chantierIntervenantComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ChantierIntervenant', async () => {
        const nbButtonsBeforeDelete = await chantierIntervenantComponentsPage.countDeleteButtons();
        await chantierIntervenantComponentsPage.clickOnLastDeleteButton();

        chantierIntervenantDeleteDialog = new ChantierIntervenantDeleteDialog();
        expect(await chantierIntervenantDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Chantier Intervenant?');
        await chantierIntervenantDeleteDialog.clickOnConfirmButton();

        expect(await chantierIntervenantComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
