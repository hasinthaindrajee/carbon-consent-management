/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.carbon.consent.mgt.core;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.consent.mgt.core.dao.PIICategoryDAO;
import org.wso2.carbon.consent.mgt.core.dao.PurposeCategoryDAO;
import org.wso2.carbon.consent.mgt.core.dao.PurposeDAO;
import org.wso2.carbon.consent.mgt.core.exception.ConsentManagementClientException;
import org.wso2.carbon.consent.mgt.core.exception.ConsentManagementException;
import org.wso2.carbon.consent.mgt.core.internal.ConsentManagerConfiguration;
import org.wso2.carbon.consent.mgt.core.model.PIICategory;
import org.wso2.carbon.consent.mgt.core.model.Purpose;
import org.wso2.carbon.consent.mgt.core.model.PurposeCategory;
import org.wso2.carbon.consent.mgt.core.util.ConsentConfigParser;
import org.wso2.carbon.consent.mgt.core.util.ConsentUtils;
import java.util.List;

import static org.wso2.carbon.consent.mgt.core.constant.ConsentConstants.ErrorMessages.ERROR_CODE_INVALID_ARGUMENTS_FOR_LIM_OFFSET;
import static org.wso2.carbon.consent.mgt.core.constant.ConsentConstants.ErrorMessages.ERROR_CODE_PURPOSE_ALREADY_EXIST;
import static org.wso2.carbon.consent.mgt.core.constant.ConsentConstants.ErrorMessages.ERROR_CODE_PURPOSE_ID_INVALID;
import static org.wso2.carbon.consent.mgt.core.constant.ConsentConstants.ErrorMessages.ERROR_CODE_PURPOSE_ID_REQUIRED;
import static org.wso2.carbon.consent.mgt.core.constant.ConsentConstants.ErrorMessages.ERROR_CODE_PURPOSE_NAME_REQUIRED;
import static org.wso2.carbon.consent.mgt.core.constant.ConsentConstants.PURPOSE_SEARCH_LIMIT_PATH;

/**
 * Consent manager service implementation.
 */
public class ConsentManager {

    private static final Log log = LogFactory.getLog(ConsentManager.class);
    private static final int DEFALT_SEARCH_LIMIT = 100;
    private PurposeDAO purposeDAO;
    private PurposeCategoryDAO purposeCategoryDAO;
    private PIICategoryDAO piiCategoryDAO;

    public ConsentManager(ConsentManagerConfiguration configuration) {
        this.purposeDAO = configuration.getPurposeDAO();
        purposeCategoryDAO = configuration.getPurposeCategoryDAO();
        piiCategoryDAO = configuration.getPiiCategoryDAO();
    }

    public Purpose addPurpose(Purpose purpose) throws ConsentManagementException {

        validateInputParameters(purpose);
        return purposeDAO.addPurpose(purpose);
    }

    public Purpose getPurpose(int purposeId) throws ConsentManagementException {
        return purposeDAO.getPurposeById(purposeId);
    }

    public Purpose getPurposeByName(String name) throws ConsentManagementException {
        return purposeDAO.getPurposeByName(name);
    }

    public List<Purpose> listPurposes(int limit, int offset) throws ConsentManagementException {

        validatePaginationParameters(limit, offset);

        if (limit == 0) {
            limit = getDefaultLimitFromConfig();
            if (log.isDebugEnabled()) {
                log.debug("Limit is not defied the request, default to :" + limit);
            }
        }
        return purposeDAO.listPurposes(limit, offset);
    }

    public void deletePurpose(int purposeId) throws ConsentManagementException {

        if (purposeId == 0 || purposeId < 0) {
            if (log.isDebugEnabled()) {
                log.debug("Purpose Id is not found in the request or invalid purpose Id");
            }
            throw ConsentUtils.handleClientException(ERROR_CODE_PURPOSE_ID_REQUIRED, null);
        }

        if (getPurpose(purposeId) == null) {
            throw ConsentUtils.handleClientException(ERROR_CODE_PURPOSE_ID_INVALID, String.valueOf(purposeId));
        }
        int id = purposeDAO.deletePurpose(purposeId);
        if (log.isDebugEnabled()) {
            log.debug("Purpose deleted successfully. ID: " + id);
        }
    }

    public boolean isPurposeExists(String name) throws ConsentManagementException {
        return getPurposeByName(name) != null;
    }

    public PurposeCategory addPurposeCategory(PurposeCategory purposeCategory) throws ConsentManagementException {
        return purposeCategoryDAO.addPurposeCategory(purposeCategory);
    }

    public PIICategory addPIICategory(PIICategory piiCategory) throws ConsentManagementException {
        return piiCategoryDAO.addPIICategory(piiCategory);
    }

    private void validateInputParameters(Purpose purpose) throws ConsentManagementException {

        if (StringUtils.isBlank(purpose.getName())) {
            if (log.isDebugEnabled()) {
                log.debug("Purpose name cannot be empty");
            }
            throw ConsentUtils.handleClientException(ERROR_CODE_PURPOSE_NAME_REQUIRED, null);
        }

        if (isPurposeExists(purpose.getName())) {
            if (log.isDebugEnabled()) {
                log.debug("A purpose already exists with name: " + purpose.getName());
            }
            throw ConsentUtils.handleClientException(ERROR_CODE_PURPOSE_ALREADY_EXIST, purpose.getName());
        }
    }

    private int getDefaultLimitFromConfig() {
        int limit = DEFALT_SEARCH_LIMIT;

        if (ConsentConfigParser.getInstance().getConfiguration().get(PURPOSE_SEARCH_LIMIT_PATH) != null) {
            limit = Integer.parseInt(ConsentConfigParser.getInstance().getConfiguration()
                    .get(PURPOSE_SEARCH_LIMIT_PATH).toString());
        }
        return limit;
    }

    private void validatePaginationParameters(int limit, int offset) throws ConsentManagementClientException {
        if (limit < 0 || offset < 0) {
            throw ConsentUtils.handleClientException(ERROR_CODE_INVALID_ARGUMENTS_FOR_LIM_OFFSET, null);
        }
    }

}
