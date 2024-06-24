var memberCount = 0;
var petCount = 0;
function addMember() {
    memberCount++;
    const divEle = document.getElementById("additional-fields")
    divEle.innerHTML += `
        <div id="member-${memberCount}" class="fields">
            <div class="heading-2">
                <p>Household Member</p>
                <div class="delete-button" onclick="deleteMember(${memberCount})">
                    <img class="delete-button" src="/png/close-icon-x.png">
                    <span class="delete-button">Delete Member</span>
                </div>
            </div>
              
              <div class="main-respondent-body">
                <div class="image-panel">
                  <div class="input-field">
                    <label for="resPfp">Upload Picture</label>
                    <input type="file" id="resPfp" name="resPfp" accept="image/jpeg, image/png" value="Upload Picture">
                  </div>
                </div>
                
                <div class="text-panel">
                  <div class="form-row">
                    <div class="input-field">
                      <label>Last Name</label>
                      <input type="text" name="resLastName"required/>
                    </div>
                    
                    <div class="input-field">
                      <label>First Name</label>
                      <input type="text" name="resFirstName" required/>
                    </div>
        
                    <div class="input-field">
                      <label>Middle Name</label>
                      <input type="text" name="resMiddleName" required/>
                    </div>
                  </div>
                  
                  <div class="form-row">
                    <div class="input-field">
                      <label>Gender</label>
                      <select id="resGender" name="resGender" required/>
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
                      <input type="text" name="resRelationToRespondent" required/>
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

function addPet() {
    petCount++;
    const divEle = document.getElementById("additional-fields")
    divEle.innerHTML += `
        <div id="pet-${petCount}" class="fields">
            <div class="heading-2">
                <p>Household Pet</p>
                <div class="delete-button" onclick="deletePet(${petCount})">
                    <img class="delete-button" src="/png/close-icon-x.png">
                    <span class="delete-button">Delete Pet</span>
                </div>
            </div>
              
              <div class="main-respondent-body">
                  <div class="form-row">
                    <div class="input-field">
                      <label>Pet Name</label>
                      <input type="text" name="petName"required/>
                    </div>
                    
                    <div class="input-field">
                      <label>Animal Type</label>
                      <input type="text" name="petAnimalType" required/>
                    </div>
                  </div>
              </div>
              <hr>
        </div>
    `;
}

function deleteMember(memberCount) {
    
    var elem = document.getElementById(`member-${memberCount}`);
    elem.parentNode.removeChild(elem);
    return false;
}

function deletePet(petCount) {
    var elem = document.getElementById(`pet-${petCount}`);
    elem.parentNode.removeChild(elem);
    return false;
}