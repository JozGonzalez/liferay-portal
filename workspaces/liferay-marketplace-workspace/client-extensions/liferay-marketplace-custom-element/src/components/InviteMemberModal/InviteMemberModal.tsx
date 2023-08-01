/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayCheckbox, ClayInput} from '@clayui/form';
import ClayModal, {useModal} from '@clayui/modal';
import {useCallback, useEffect, useMemo, useState} from 'react';

import './inviteMemberModal.scss';

import {DisplayType} from '@clayui/alert';
import ClayIcon from '@clayui/icon';

import {Liferay} from '../../liferay/liferay';
import {getMyUserAccount} from '../../utils/api';
import {createPassword} from '../../utils/createPassword';
import {
	addAdditionalInfo,
	addAdminRegularRole,
	addExistentUserIntoAccount,
	createNewUser,
	getAccountRolesOnAPI,
	getSiteURL,
	getUserByEmail,
	sendRoleAccountUser,
} from './services';

interface InviteMemberModalProps {
	dashboardType: false | 'customer-dashboard' | 'publisher-dashboard';
	handleClose: () => void;
	listOfRoles: string[];
	renderToast: (message: string, title: string, type: DisplayType) => void;
	rolesPermissionDescription: {
		appPermissions: PermissionDescription[];
		dashboardPermissions: PermissionDescription[];
	};
	selectedAccount: Account;
}

export function InviteMemberModal({
	dashboardType,
	handleClose,
	listOfRoles,
	renderToast,
	rolesPermissionDescription,
	selectedAccount,
}: InviteMemberModalProps) {
	const {observer, onClose} = useModal({
		onClose: () => handleClose(),
	});

	const [formFields, setFormFields] = useState({
		email: '',
		firstName: '',
		lastName: '',
	});

	const [checkboxRoles, setCheckboxRoles] = useState<CheckboxRole[]>([]);
	const [formValid, setFormValid] = useState<boolean>(false);

	const [accountRoles, setAccountRoles] = useState<AccountRole[]>();
	const [userPassword, setUserPassword] = useState<string>('');

	const getAccountRoles = useCallback(async () => {
		const roles = await getAccountRolesOnAPI(selectedAccount.id);

		setAccountRoles(roles);
	}, [selectedAccount.id]);

	useEffect(() => {
		const mapRoles = listOfRoles.map((role) => {
			return {isChecked: false, roleName: role};
		});

		setCheckboxRoles(mapRoles);
		getAccountRoles();
		setUserPassword(createPassword());
	}, [getAccountRoles, listOfRoles]);

	const finalPathUrl = {
		'customer-dashboard': 'customer-gate',
		'publisher-dashboard': 'loading',
	};

	const emailInviteURL = `${Liferay.ThemeDisplay.getPortalURL()}/c/login?redirect=${getSiteURL()}/${
		finalPathUrl[dashboardType as keyof typeof finalPathUrl]
	}`;

	const jsonBody = useMemo(
		() => ({
			alternateName: formFields.email.replace('@', '-'),
			emailAddress: formFields.email,
			familyName: formFields.lastName,
			givenName: formFields.firstName,
			password: userPassword,
		}),
		[
			formFields.email,
			formFields.firstName,
			formFields.lastName,
			userPassword,
		]
	);

	const getCheckedRoles = () => {
		let checkedRole = '';

		for (const checkboxRole of checkboxRoles) {
			if (checkboxRole.isChecked) {
				checkedRole = checkedRole + checkboxRole.roleName + '/';
			}
		}

		return checkedRole;
	};

	const checkIfUserIsInvited = (user: UserAccount, accountId: number) =>
		!!user.accountBriefs.find(
			(accountBrief) => accountBrief.id === accountId
		);

	const addAccountRolesToUser = async (user: UserAccount) => {
		for (const checkboxRole of checkboxRoles) {
			if (checkboxRole.isChecked) {
				const matchingAccountRole = accountRoles?.find(
					(accountRole: AccountRole) =>
						accountRole.name === 'Invited Member'
				);

				if (matchingAccountRole) {
					await sendRoleAccountUser(
						selectedAccount.id,
						matchingAccountRole.id,
						user.id
					);
				}
			}
		}
	};

	const handleSubmit = async (event: React.FormEvent) => {
		event.preventDefault();

		if (!formValid) {
			return;
		}

		// eslint-disable-next-line prefer-const
		let [user, myUser] = await Promise.all([
			getUserByEmail(formFields.email),
			getMyUserAccount(),
		]);

		if (user && checkIfUserIsInvited(user, selectedAccount.id)) {
			renderToast(
				"There's already a user with this email invited to this account",
				'',
				'danger'
			);

			return onClose();
		}

		if (!user) {
			user = await createNewUser(jsonBody);
		}
		if (
			checkboxRoles.some(
				(role) =>
					role.roleName === 'Account Administrator' && role.isChecked
			)
		) {
			await addAdminRegularRole(user.id);
		}

		await addExistentUserIntoAccount(selectedAccount.id, formFields.email);
		await addAccountRolesToUser(user);

		await addAdditionalInfo({
			acceptInviteStatus: false,
			accountName: selectedAccount.name,
			emailOfMember: formFields.email,
			inviteURL: emailInviteURL,
			inviterName: myUser.givenName,
			mothersName: userPassword,
			r_accountEntryToUserAdditionalInfo_accountEntryId:
				selectedAccount.id,
			r_userToUserAddInfo_userId: user.id,
			roles: getCheckedRoles(),
			sendType: {key: 'shipping', name: 'Shipping'},
			userFirstName: formFields.firstName,
		});

		renderToast(
			'invited succesfully',
			`${user.givenName} ${user.familyName}`,
			'success'
		);

		onClose();
	};

	const validateForm = (checkboxValues: CheckboxRole[]) => {
		const isValid = checkboxValues.some(
			(checkbox: CheckboxRole) => checkbox.isChecked
		);

		setFormValid(isValid);
	};

	const handleCheck = (selectedRoleName: String) => {
		const rolesChecked = checkboxRoles.map((role) => {
			if (selectedRoleName === role.roleName) {
				role.isChecked = !role.isChecked;

				return role;
			}

			return role;
		}, []);
		setCheckboxRoles(rolesChecked);
		validateForm(rolesChecked);
	};

	return (
		<ClayModal observer={observer} size="lg">
			<ClayModal.Header>Invite New Member</ClayModal.Header>

			<ClayModal.Body>
				<ClayForm onSubmit={handleSubmit}>
					<ClayForm.Group>
						<div>
							<ClayModal.TitleSection>
								<ClayModal.Title>Invite</ClayModal.Title>
							</ClayModal.TitleSection>

							<hr className="solid"></hr>
						</div>

						<div className="d-flex justify-content-between pb-5">
							<div className="form-group pr-3 w-50">
								<label
									className="control-label pb-1"
									htmlFor="firstName"
								>
									First Name
								</label>

								<ClayInput
									id="firstName"
									onChange={(event) => {
										setFormFields({
											...formFields,
											firstName: event.target.value,
										});
									}}
									required={true}
									type="text"
								/>
							</div>

							<div className="form-group pl-3 w-50">
								<label
									className="control-label pb-1"
									htmlFor="lastName"
								>
									Last Name
								</label>

								<ClayInput
									id="lastName"
									onChange={(event) => {
										setFormFields({
											...formFields,
											lastName: event.target.value,
										});
									}}
									required={true}
									type="text"
								/>
							</div>
						</div>

						<div className="form-group">
							<label
								className="control-label pb-1"
								htmlFor="emailAddress"
							>
								Email
							</label>

							<ClayInput
								id="emailAddress"
								onChange={(event) => {
									setFormFields({
										...formFields,
										email: event.target.value,
									});
								}}
								required={true}
								type="text"
							/>
						</div>
					</ClayForm.Group>

					<ClayForm.Group>
						<div className="pt-4">
							<ClayModal.TitleSection>
								<ClayModal.Title className="control-label">
									Role
								</ClayModal.Title>
							</ClayModal.TitleSection>

							<hr className="solid" />
						</div>

						<div>
							{listOfRoles.map((role, index) => {
								return (
									<ClayCheckbox
										checked={
											checkboxRoles[index]?.isChecked
										}
										key={index}
										label={role}
										onChange={() => handleCheck(role)}
										required={!formValid}
										value={role}
									/>
								);
							})}
						</div>
					</ClayForm.Group>

					<ClayForm.Group>
						<ClayModal.TitleSection>
							<ClayModal.Title className="control-label">
								App & Solution Permissions
							</ClayModal.Title>
						</ClayModal.TitleSection>

						<hr className="solid"></hr>

						{rolesPermissionDescription.appPermissions.map(
							(rolePermission, index) => {
								const showCheckIcon = checkboxRoles.some(
									(checkedRole) =>
										checkedRole.isChecked &&
										rolePermission.permittedRoles.includes(
											checkedRole.roleName
										)
								);

								return (
									<div className="p-2 text-muted" key={index}>
										<ClayIcon
											className={
												showCheckIcon
													? 'text-success mr-2'
													: 'mr-2'
											}
											symbol={
												showCheckIcon
													? 'check'
													: 'block'
											}
										/>

										{rolePermission.permissionName}
									</div>
								);
							}
						)}
					</ClayForm.Group>

					<ClayForm.Group>
						<ClayModal.TitleSection>
							<ClayModal.Title className="control-label">
								Dashboard Permissions
							</ClayModal.Title>
						</ClayModal.TitleSection>

						<hr className="solid"></hr>

						{rolesPermissionDescription.dashboardPermissions.map(
							(rolePermission, index) => {
								const showCheckIcon = checkboxRoles.some(
									(checkedRole) =>
										checkedRole.isChecked &&
										rolePermission.permittedRoles.includes(
											checkedRole.roleName
										)
								);

								return (
									<div className="p-2 text-muted" key={index}>
										<ClayIcon
											className={
												showCheckIcon
													? 'text-success mr-2'
													: 'mr-2'
											}
											symbol={
												showCheckIcon
													? 'check'
													: 'block'
											}
										/>

										{rolePermission.permissionName}
									</div>
								);
							}
						)}
					</ClayForm.Group>

					<ClayButton.Group
						className="d-flex justify-content-between justify-content-lg-end modal-footer"
						spaced
					>
						<ClayButton
							className="cancelButton"
							onClick={() => onClose()}
							outline={true}
							type="button"
						>
							Cancel
						</ClayButton>

						<ClayButton type="submit">Send Invite</ClayButton>
					</ClayButton.Group>
				</ClayForm>
			</ClayModal.Body>
		</ClayModal>
	);
}
