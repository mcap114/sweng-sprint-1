var members = []
var pets = []
function addMember() {
    const divEle = document.getElementById("additional-fields")
    divEle.innerHTML += `
        <div class="fields">
            <div class="heading-2">
                <p>Household Member</p>
                <div class="delete-button">
                    <span class="delete-button">Delete Member</span>
                </div>
            </div>
              
              <div class="main-respondent-body">
                <div class="image-panel">
                  <div class="input-field">
                    <label for="resPfp">Upload Picture</label>
                    <input type="file" id="memPfp" name="memPfp" accept="image/jpeg, image/png" value="Upload Picture">
                  </div>
                </div>
                
                <div class="text-panel">
                  <div class="form-row">
                    <div class="input-field">
                      <label>Last Name</label>
                      <input type="text" name="memLastName"required/>
                    </div>
                    
                    <div class="input-field">
                      <label>First Name</label>
                      <input type="text" name="memFirstName" required/>
                    </div>
        
                    <div class="input-field">
                      <label>Middle Name</label>
                      <input type="text" name="memMiddleName" required/>
                    </div>
                  </div>
                  
                  <div class="form-row">
                    <div class="input-field">
                      <label>Gender</label>
                      <select id="memGender" name="memGender" required/>
                        <option disabled selected value>---</option>
                        <option value="male">Male</option>
                        <option value="female">Female</option>
                      </select>
                    </div>
        
                    <div class="input-field">
                      <label>Birthday</label>
                      <input type="date" name="resBirthday" required/>
                    </div>
        
                    <div class="input-field">
                      <label>Civil Status</label>
                      <select id="resCivilStatus" name="resCivilStatus" required>
                        <option disabled selected value>---</option>
                        <option value="single">Single</option>
                        <option value="married">Married</option>
                        <option value="separated">Separated</option>
                        <option value="widowed">Widowed</option>
                      </select>
                    </div>
        
                    <div class="input-field">
                      <label>Contact Number</label>
                      <input type="tel" name="resContactNumber" placeholder="09XX XXX XXXX" pattern="09[0-9]{2} [0-9]{3} [0-9]{4}" required/>
                    </div>
                  </div>
                  
                  <div class="form-row">
                    <div class="input-field">
                      <label>Highest Educational Attainment</label>
                      <select id="resHighestEducationalAttainment" name="resHighestEducationalAttainment" required>
                        <option disabled selected value>---</option>
                        <option value="noEducation"> No Education</option>
                        <option value="elementaryUndergraduate">Elementary Undergraduate</option>
                        <option value="elementaryGraduate">Elementary Graduate</option>
                        <option value="highschoolUndergraduate">High School Undergraduate</option>
                        <option value="highschoolGraduate">High School Graduate</option>
                        <option value="collegeUndergraduate">College Undergraduate</option>
                        <option value="collegeGraduateOrHigher">College Graduate or Higher</option>
                      </select>
                    </div>
        
                    <div class="input-field">
                      <label>Occupation</label>
                      <input type="text" name="resOccupation" required/>
                    </div>
        
                    <div class="input-field">
                      <label>Monthly Income</label>
                      <input type="number" name="resMonthlyIncome" required/>
                    </div>

                    <div class="input-field">
                      <label>Relationship to Main Respondent</label>
                      <input type="text" name="memRelationToRespondent" required/>
                    </div>
                  </div>
                  
                  <div class="form-row">
                    <div class="input-field">
                      <label>Health Status</label>
                      <input type="text" name="resHealthStatus" required/>
                    </div>
        
                    <div class="input-field">
                      <label>PWD Type</label>
                      <input type="text" name="resPwdType" />
                    </div>
                  </div>
                </div>
              </div>
              <hr>
            </div>
    `;
}